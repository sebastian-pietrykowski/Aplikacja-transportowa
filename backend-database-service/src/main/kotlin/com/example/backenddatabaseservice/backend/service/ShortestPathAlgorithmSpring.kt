package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import org.springframework.stereotype.Service
import java.util.PriorityQueue
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Service
class ShortestPathAlgorithmSpring(
    private val connectionsFinder: FromStopConnectionsFinder
) {
    private val visited = HashSet<StopWithTime>()
    private val predecessors = HashMap<StopWithTime, StopConnectionWithDeparture?>() // arrivalStop, connectionToIt
    private val totalTimeWithPenalty = HashMap<StopWithTime, Int>() // arrivalStop, totalTimeToIt
    private val queue = PriorityQueue<Pair<StopWithTime, Int>>(
        compareBy({ it.second }, { it.first.departureTime })
    ) // arrivalStop, timeDifferenceWithPenalty
    private val path = mutableListOf<StopConnectionWithArrival>()
    private var minTimeDestinationStopsContainer: MinTimeDestinationStopsContainer? = null // only for stops with destinationId

    private fun initializeValues(destinationComplexId: String) {
        visited.clear()
        predecessors.clear()
        totalTimeWithPenalty.clear()
        queue.clear()
        path.clear()
        minTimeDestinationStopsContainer = MinTimeDestinationStopsContainer(destinationComplexId)
    }

    private fun findShortestConnections(sourceStopWithTime: StopWithTime, destinationComplexId: String) {
        queue.add(sourceStopWithTime to 0)
        totalTimeWithPenalty[sourceStopWithTime] = 0
        while (queue.isNotEmpty()) {
            val (actualStop, actualCost) = queue.poll()
            visited.add(actualStop)
            val isStart = predecessors[actualStop] == null
            val lastLineNumber = predecessors[actualStop]?.simpleConnection?.lineNumber
            val connections = connectionsFinder.find(actualStop, isStart, lastLineNumber)
            for (it in connections) {
                val adjustedStop = it.consideredStopWithTime
                val newCost = actualCost + it.simpleConnection!!.timeDifferenceWithPenalty
                totalTimeWithPenalty[adjustedStop] = totalTimeWithPenalty[adjustedStop] ?: Int.MAX_VALUE
                val conditionForBetterStop = newCost < totalTimeWithPenalty[adjustedStop]!!
                        && newCost < Constraints.MAX_ACCEPTABLE_TIME_WITH_PENALTY_IN_MINUTES
                if (conditionForBetterStop) {
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

    private fun findNearestDestinationStop(): StopWithTime? {
        return minTimeDestinationStopsContainer!!.getMin()
    }


    fun find(sourceStopWithTime: StopWithTime, destinationComplexId: String): List<StopConnectionWithArrival> {
        initializeValues(destinationComplexId)
        findShortestConnections(sourceStopWithTime, destinationComplexId)
//        val possibleDestinations = stopComplexService.findStopsByComplexId(destinationComplexId)
//            .mapNotNull { d -> d.id }
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