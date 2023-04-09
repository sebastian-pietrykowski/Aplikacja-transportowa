package com.example.backend.service

import com.example.backend.domain.Stop
import com.example.backend.domain.StopEdge
import java.util.PriorityQueue
import kotlin.collections.HashMap
import kotlin.collections.HashSet
// zmienić tak, aby linie były krawędziami
class ShortestPathAlgorithm (
    val connections: Map<Stop, List<StopEdge>>, val sourceStop: Stop) {
    fun find(destinationId: Int): HashMap<Stop, Stop?> {
        val n = connections.size
        val visited = HashSet<Stop>(n)
        val predecessors = HashMap<Stop, Stop?>(n).apply {
            connections.forEach{ put(it.key, null) }
        }
        var totalCost = HashMap<Stop, Int>(n).apply {
            connections.forEach{ put(it.key, Int.MAX_VALUE) }
        }
        val queue = PriorityQueue<Pair<Stop,Int>>(
            n, compareBy ({ it.second }, { it.first.departureTime })
        )

        queue.add(sourceStop to 0)
        totalCost[sourceStop] = 0
        while (queue.isNotEmpty()) {
            val (actualStop, actualCost) = queue.poll()
            visited.add(actualStop)
            connections[actualStop]?.forEach {
                val adjustedStop = it.stop
                val newCost = actualCost + it.timeDifference
                if (totalCost[adjustedStop]!! > newCost) {
                    totalCost[adjustedStop] = newCost
                    queue.offer(adjustedStop to newCost)
                    predecessors[adjustedStop] = actualStop
                }
            }
        }

        return predecessors
    }


}