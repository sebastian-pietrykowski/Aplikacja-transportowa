package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import java.util.PriorityQueue
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ShortestPathAlgorithm(
    private val connections: Map<StopWithTime, List<StopConnectionWithArrival>>, private val sourceStopWithTime: StopWithTime
) {
    private val n = connections.size
    private val visited = HashSet<StopWithTime>(n)
    private val predecessors = HashMap<StopWithTime, StopConnectionWithDeparture?>(n).apply {
        connections.forEach { put(it.key, null) }
    } // arrivalStop, connectionToIt
    private val totalTimeWithPenalty = HashMap<StopWithTime, Int>(n).apply {
        connections.forEach { put(it.key, Int.MAX_VALUE) }
    } // arrivalStop, totalTimeToIt
    private val queue = PriorityQueue<Pair<StopWithTime, Int>>(
        n, compareBy({ it.second }, { it.first.departureTime })
    ) // arrivalStop, timeDifferenceWithPenalty
    private var minTimeDestinationStopsContainer: MinTimeDestinationStopsContainer? = null // only for stops with destinationId
    private val path = mutableListOf<StopConnectionWithArrival>()

//    private val connectionsFinder = FromStopConnectionsFinder()

    private fun findShortestConnections(destinationComplexId: String) {
        queue.add(sourceStopWithTime to 0)
        totalTimeWithPenalty[sourceStopWithTime] = 0
        while (queue.isNotEmpty()) {
            val (actualStop, actualCost) = queue.poll()
            visited.add(actualStop)
            //connectionsFinder.find(actualStop)
            connections[actualStop]?.forEach {
                val adjustedStop = it.consideredStopWithTime
                val newCost = actualCost + it.simpleConnection!!.timeDifferenceWithPenalty
                if (totalTimeWithPenalty[adjustedStop]!! > newCost) {
                    totalTimeWithPenalty[adjustedStop] = newCost
                    queue.offer(adjustedStop to newCost)
                    predecessors[adjustedStop] = StopConnectionWithDeparture(
                        actualStop, it.simpleConnection
                    )
                    if (adjustedStop.complexId == destinationComplexId)
                        minTimeDestinationStopsContainer?.add(it.consideredStopWithTime, newCost)
                }
            }
        }
    }

    private fun findPath(destinationStopWithTime: StopWithTime) {
        var currentStopWithTime: StopWithTime? = destinationStopWithTime
        var currentSimpleConnection: SimpleStopConnection? = null
        while (currentStopWithTime != null) {
            val predecessor = predecessors[currentStopWithTime]
            val currentConnection = StopConnectionWithArrival(currentStopWithTime, currentSimpleConnection)
            path.add(currentConnection)
            currentStopWithTime = predecessor?.departureStopWithTime
            currentSimpleConnection = predecessor?.simpleConnection
        }
        path.reverse()
    }
    private fun findNearestDestinationStop() : StopWithTime? {
        return minTimeDestinationStopsContainer?.getMin()
    }


    fun find(destinationComplexId: String): List<StopConnectionWithArrival> {
        minTimeDestinationStopsContainer = MinTimeDestinationStopsContainer(destinationComplexId)
        findShortestConnections(destinationComplexId)
        val destinationStop = findNearestDestinationStop()
        findPath(destinationStop!!)

        println("Final destination: $destinationStop")
        println("\nPredecessors:")
        predecessors.forEach { (k, v) -> println("$k = $v") }
        println("\nTotal time with penalty:")
        totalTimeWithPenalty.forEach { (k, v) -> println("$k = $v") }
        println("\nPath:")
        path.forEach { (k, v) -> println("$k = $v") }

        return path
    }


}