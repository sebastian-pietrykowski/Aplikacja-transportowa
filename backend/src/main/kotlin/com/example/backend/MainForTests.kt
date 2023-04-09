package com.example.backend

import com.example.backend.domain.Stop
import com.example.backend.domain.StopEdge
import com.example.backend.domain.StopType.*
import com.example.backend.service.ShortestPathAlgorithm
import java.time.LocalTime
import java.time.temporal.ChronoUnit



class MainForTests

fun main() {
    println("Ala ma kota")
    val stop1 = Stop(1, LocalTime.of(10, 31), 35, BUS)

    // case 1
    val listOfStops = listOf<Stop>(
        Stop(1, LocalTime.of(0,0), 1, BUS),
        Stop(2, LocalTime.of(0,1), 1, BUS),
        Stop(2, LocalTime.of(0,1), 3, BUS),
        Stop(3, LocalTime.of(0,2), 1, BUS),
        Stop(3, LocalTime.of(0,2), 2, BUS),
        Stop(4, LocalTime.of(0,3), 1, BUS),
        Stop(5, LocalTime.of(0,3), 2, BUS),
        Stop(6, LocalTime.of(0,2), 3, BUS)
    )

    val connections = mapOf<Stop, List<StopEdge>>(
        listOfStops[0] to listOf<StopEdge>(
            StopEdge(listOfStops[0], listOfStops[1])
        ),
        listOfStops[1] to listOf<StopEdge>(
            StopEdge(listOfStops[1], listOfStops[2]),
            StopEdge(listOfStops[1], listOfStops[3])
        ),
        listOfStops[2] to listOf<StopEdge>(
            StopEdge(listOfStops[2], listOfStops[7])
        ),
        listOfStops[3] to listOf<StopEdge>(
            StopEdge(listOfStops[3], listOfStops[4]),
            StopEdge(listOfStops[3], listOfStops[5])
        ),
        listOfStops[4] to listOf<StopEdge>(
            StopEdge(listOfStops[4], listOfStops[6])
        ),
        listOfStops[5] to emptyList<StopEdge>(),
        listOfStops[6] to emptyList<StopEdge>(),
        listOfStops[7] to emptyList<StopEdge>()
    )

    val result = ShortestPathAlgorithm(connections, listOfStops[0]).find(4)
    println(result)

//    ChronoUnit.MINUTES.between()
    //val a1 = LocalTime.of(10,55).until(LocalTime.of(10,50), ChronoUnit.MINUTES)
//    println(LocalTime.of(10,45).compareTo(LocalTime.of(10,50)))
}