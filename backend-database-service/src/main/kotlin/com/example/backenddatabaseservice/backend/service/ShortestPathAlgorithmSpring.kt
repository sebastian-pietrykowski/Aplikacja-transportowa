package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import com.example.backenddatabaseservice.database.service.StopComplexService
import com.example.backenddatabaseservice.database.service.StopService
import com.example.backenddatabaseservice.database.service.TimeStopConnectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.PriorityQueue
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Service
class ShortestPathAlgorithmSpring(
    private val stopComplexService: StopComplexService,
    private val timeStopConnectionService: TimeStopConnectionService,
    private val connectionsFinder: FromStopConnectionsFinder

    //private val connections: Map<Stop, List<StopConnectionWithArrival>>,

) {
    //    private val n: Int = timeStopConnectionService.getLength().toInt()
    private val visited = HashSet<StopWithTime>()
    private val predecessors = HashMap<StopWithTime, StopConnectionWithDeparture?>() // arrivalStop, connectionToIt
    private val totalTimeWithPenalty = HashMap<StopWithTime, Int>() // arrivalStop, totalTimeToIt
    private val queue = PriorityQueue<Pair<StopWithTime, Int>>(
        compareBy({ it.second }, { it.first.departureTime })
    ) // arrivalStop, timeDifferenceWithPenalty
    private val minTimeStopsContainer = MinTimeStopsContainer() // only for stops with destinationId
    private val path = mutableListOf<StopConnectionWithArrival>()


    private fun findShortestConnections(sourceStopWithTime: StopWithTime, destinationComplexId: String) {
        queue.add(sourceStopWithTime to 0)
        totalTimeWithPenalty[sourceStopWithTime] = 0
        while (queue.isNotEmpty()) {
            val (actualStop, actualCost) = queue.poll()
            visited.add(actualStop)
            connectionsFinder.find(actualStop)?.forEach {
                val adjustedStop = it.consideredStopWithTime
                val newCost = actualCost + it.simpleConnection!!.timeDifferenceWithPenalty
                totalTimeWithPenalty?.get(adjustedStop) ?: totalTimeWithPenalty.set(adjustedStop, Int.MAX_VALUE)
                if (totalTimeWithPenalty[adjustedStop]!! > newCost) {
                    totalTimeWithPenalty[adjustedStop] = newCost
                    queue.offer(adjustedStop to newCost)
                    predecessors[adjustedStop] = StopConnectionWithDeparture(
                        actualStop, it.simpleConnection
                    )
                    if (adjustedStop.complexId == destinationComplexId)
                        minTimeStopsContainer.add(it.consideredStopWithTime, newCost)
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

    private fun findNearestDestinationStop(stopsInComplex: List<String>): StopWithTime? {
        return minTimeStopsContainer.find(stopsInComplex)
    }


    fun find(sourceStopWithTime: StopWithTime, destinationComplexId: String): List<StopConnectionWithArrival> {
        findShortestConnections(sourceStopWithTime, destinationComplexId)
        val possibleDestinations = stopComplexService.findStopsByComplexId(destinationComplexId)
            .mapNotNull { d -> d.id }
        val destinationStop = findNearestDestinationStop(possibleDestinations)
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