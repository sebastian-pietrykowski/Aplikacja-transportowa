package com.example.backend.service

import com.example.backend.domain.*
import java.util.PriorityQueue
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ShortestPathAlgorithm(
    private val connections: Map<Stop, List<StopConnectionWithArrival>>, private val sourceStop: Stop
) {
    private val n = connections.size
    private val visited = HashSet<Stop>(n)
    private val predecessors = HashMap<Stop, StopConnectionWithDeparture?>(n).apply {
        connections.forEach { put(it.key, null) }
    } // arrivalStop, connectionToIt
    private val totalTimeWithPenalty = HashMap<Stop, Int>(n).apply {
        connections.forEach { put(it.key, Int.MAX_VALUE) }
    } // arrivalStop, totalTimeToIt
    private val queue = PriorityQueue<Pair<Stop, Int>>(
        n, compareBy({ it.second }, { it.first.departureTime })
    ) // arrivalStop, timeDifferenceWithPenalty
    private val minTimeStopsContainer = MinTimeStopsContainer() // only for stops with destinationId
    private val path = mutableListOf<StopConnectionWithArrival>()

    private fun findShortestConnections(destinationId: Int) {
        queue.add(sourceStop to 0)
        totalTimeWithPenalty[sourceStop] = 0
        while (queue.isNotEmpty()) {
            val (actualStop, actualCost) = queue.poll()
            visited.add(actualStop)
            connections[actualStop]?.forEach {
                val adjustedStop = it.consideredStop
                val newCost = actualCost + it.simpleConnection!!.timeDifferenceWithPenalty
                if (totalTimeWithPenalty[adjustedStop]!! > newCost) {
                    totalTimeWithPenalty[adjustedStop] = newCost
                    queue.offer(adjustedStop to newCost)
                    predecessors[adjustedStop] = StopConnectionWithDeparture(
                        actualStop, it.simpleConnection
                    )
                    if (adjustedStop.locationId == destinationId)
                        minTimeStopsContainer.add(it.consideredStop, newCost)
                }
            }
        }
    }

    private fun findPath(destinationId: Int) {
        var currentStop = minTimeStopsContainer.find(destinationId)
        var currentSimpleConnection: SimpleStopConnection? = null
        while (currentStop != null) {
            val predecessor = predecessors[currentStop]
            val currentConnection = StopConnectionWithArrival(currentStop, currentSimpleConnection)
            path.add(currentConnection)
            currentStop = predecessor?.departureStop
            currentSimpleConnection = predecessor?.simpleConnection
        }
        path.reverse()
    }

    fun find(destinationId: Int): List<StopConnectionWithArrival> {
        findShortestConnections(destinationId)
        findPath(destinationId)

        println("Final destination: ${minTimeStopsContainer.find(destinationId)}")
        println("\nPredecessors:")
        predecessors.forEach { (k, v) -> println("$k = $v") }
        println("\nTotal time with penalty:")
        totalTimeWithPenalty.forEach { (k, v) -> println("$k = $v") }
        println("\nPath:")
        path.forEach { (k, v) -> println("$k = $v") }

        return path
    }


}