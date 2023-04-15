package com.example.backend

import com.example.backend.domain.*
import com.example.backend.domain.StopConnectionType.*
import com.example.backend.domain.StopType.*
import com.example.backend.service.ShortestPathAlgorithm
import java.time.LocalTime


class MainForTests

fun listOfStops1(nr: Int): Stop {
    return when (nr) {
        0 -> Stop(1, LocalTime.of(0, 0), BUS)
        1 -> Stop(2, LocalTime.of(0, 1), BUS)
        2 -> Stop(2, LocalTime.of(0, 2), BUS)
        3 -> Stop(6, LocalTime.of(0, 3), BUS)
        4 -> Stop(3, LocalTime.of(0, 2), BUS)
        5 -> Stop(4, LocalTime.of(0, 3), BUS)
        6 -> Stop(3, LocalTime.of(0, 3), BUS)
        7 -> Stop(5, LocalTime.of(0, 4), BUS)
        else -> Stop(-100, LocalTime.of(0, 0), BUS)
    }
}

fun listOfStopsCase2(nr: Int): Stop {
    return when (nr) {
        0 -> Stop(1, LocalTime.of(0, 0), BUS)
        1 -> Stop(2, LocalTime.of(0, 2), BUS)
        2 -> Stop(2, LocalTime.of(0, 5), BUS)
        3 -> Stop(3, LocalTime.of(0, 6), BUS)
        4 -> Stop(2, LocalTime.of(0,4), BUS)
        else -> Stop(-100, LocalTime.of(0, 0), BUS)
    }
}

fun listOfStopsCase3(nr: Int): Stop {
    return when (nr) {
        0 -> Stop(1, LocalTime.of(0, 0), BUS)
        1 -> Stop(3, LocalTime.of(0, 2), BUS)
        2 -> Stop(3, LocalTime.of(0, 3), BUS)
        3 -> Stop(4, LocalTime.of(0, 5), BUS)
        4 -> Stop(4, LocalTime.of(0,6), BUS)
        5 -> Stop(5, LocalTime.of(0,7), BUS)
        6 -> Stop(2, LocalTime.of(0,4), BUS)
        7 -> Stop(5, LocalTime.of(0,9), BUS)
        else -> Stop(-100, LocalTime.of(0, 0), BUS)
    }
}

fun main() {
    /*
    // case 1
    val connections = mapOf(
        listOfStops(0) to listOf(
            StopConnectionWithArrival(
                listOfStops(1), SimpleStopConnection(
                    1, SAME_LINE,
                    TimeDifferenceFinder.find(listOfStops(0), listOfStops(1))
                )
            )
        ),
        listOfStops(1) to listOf(
            StopConnectionWithArrival(
                listOfStops(2), SimpleStopConnection(
                    CHANGE, TimeDifferenceFinder.find(listOfStops(1), listOfStops(2))
                )
            ),
            StopConnectionWithArrival(
                listOfStops(4), SimpleStopConnection(
                    1, CHANGE,
                    TimeDifferenceFinder.find(listOfStops(1), listOfStops(4))
                )
            )
        ),
        listOfStops(2) to listOf(
            StopConnectionWithArrival(
                listOfStops(3), SimpleStopConnection(
                    3, SAME_LINE,
                    TimeDifferenceFinder.find(listOfStops(2), listOfStops(3))
                )
            )
        ),
        listOfStops(3) to emptyList(),
        listOfStops(4) to listOf(
            StopConnectionWithArrival(
                listOfStops(5), SimpleStopConnection(
                    1, SAME_LINE,
                    TimeDifferenceFinder.find(listOfStops(4), listOfStops(5))
                )
            ),
            StopConnectionWithArrival(
                listOfStops(6), SimpleStopConnection(
                    CHANGE,
                    TimeDifferenceFinder.find(listOfStops(4), listOfStops(6))
                )
            )
        ),
        listOfStops(5) to emptyList(),
        listOfStops(6) to listOf(
            StopConnectionWithArrival(
                listOfStops(7), SimpleStopConnection(
                    2, SAME_LINE,
                    TimeDifferenceFinder.find(listOfStops(6), listOfStops(7))
                )
            )
        ),
        listOfStops(7) to emptyList()
    )

    val result = ShortestPathAlgorithm(
        connections, listOfStops(0)
    ).find(4)
    result.forEach { (k, v) -> println("$k = $v") }
    */

    /*
    // case 2
    val connections = mapOf(
        listOfStopsCase2(0) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase2(1), SimpleStopConnection(
                    INITIAL_WAITING,
                    TimeDifferenceFinder.find(listOfStopsCase2(0), listOfStopsCase2(1))
                )
            ),
            StopConnectionWithArrival(
                listOfStopsCase2(4), SimpleStopConnection(
                    1, INITIAL_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase2(0), listOfStopsCase2(4))
                )
            )
        ),
        listOfStopsCase2(1) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase2(2), SimpleStopConnection(
                    2, INITIAL_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase2(1), listOfStopsCase2(2))
                )
            )
        ),
        listOfStopsCase2(2) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase2(3), SimpleStopConnection(
                    2, SAME_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase2(2), listOfStopsCase2(3))
                )
            )
        ),
        listOfStopsCase2(3) to emptyList(),
        listOfStopsCase2(4) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase2(2), SimpleStopConnection(
                    CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase2(4), listOfStopsCase2(2))
                )
            )
        )
    )

    val result = ShortestPathAlgorithm(
        connections, listOfStops(0)
    ).find(3)
    result.forEach { (k, v) -> println("$k = $v") }
    */

    // case 2
    val connections = mapOf(
        listOfStopsCase3(0) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(1), SimpleStopConnection(
                    1, INITIAL_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase3(0), listOfStopsCase3(1))
                )
            ),
            StopConnectionWithArrival(
                listOfStopsCase3(6), SimpleStopConnection(
                    4, INITIAL_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase3(0), listOfStopsCase3(6))
                )
            )
        ),
        listOfStopsCase3(1) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(2), SimpleStopConnection(
                    CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase3(1), listOfStopsCase3(2))
                )
            )
        ),
        listOfStopsCase3(2) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(3), SimpleStopConnection(
                    2, INITIAL_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase3(2), listOfStopsCase3(3))
                )
            )
        ),
        listOfStopsCase3(3) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(4), SimpleStopConnection(
                    CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase3(3), listOfStopsCase3(4))
                )
            )
        ),
        listOfStopsCase3(4) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(5), SimpleStopConnection(
                    3, INITIAL_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase3(4), listOfStopsCase3(5))
                )
            )
        ),
        listOfStopsCase3(5) to emptyList(),
        listOfStopsCase3(6) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(7), SimpleStopConnection(
                    4, SAME_LINE,
                    TimeDifferenceFinder.find(listOfStopsCase3(6), listOfStopsCase3(7))
                )
            )
        ),
        listOfStopsCase3(7) to emptyList()
    )

    ShortestPathAlgorithm(
        connections, listOfStopsCase3(0)
    ).find(5)
    //result.forEach { (k, v) -> println("$k = $v") }
//    ChronoUnit.MINUTES.between()
    //val a1 = LocalTime.of(10,55).until(LocalTime.of(10,50), ChronoUnit.MINUTES)
//    println(LocalTime.of(10,45).compareTo(LocalTime.of(10,50)))
}