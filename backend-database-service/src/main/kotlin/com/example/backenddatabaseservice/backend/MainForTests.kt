package com.example.backenddatabaseservice.backend

import com.example.backenddatabaseservice.backend.model.SimpleStopConnection
import com.example.backenddatabaseservice.backend.model.StopWithTime
import com.example.backenddatabaseservice.backend.model.StopConnectionType.*
import com.example.backenddatabaseservice.backend.model.StopConnectionWithArrival
import com.example.backenddatabaseservice.backend.model.StopType.*
import com.example.backenddatabaseservice.backend.service.ShortestPathAlgorithm
import com.example.backenddatabaseservice.backend.service.TimeDifferenceFinder
import java.time.LocalTime


class MainForTests

fun listOfStopsCase1(nr: Int): StopWithTime {
    return when (nr) {
        0 -> StopWithTime("1.1", "1", LocalTime.of(0, 0), "a", BUS)
        1 -> StopWithTime("2.1", "2", LocalTime.of(0, 1), "a", BUS)
        2 -> StopWithTime("2.1", "2", LocalTime.of(0, 2), "a", BUS)
        3 -> StopWithTime("6.1", "6", LocalTime.of(0, 3), "a", BUS)
        4 -> StopWithTime("3.1", "3", LocalTime.of(0, 2), "a", BUS)
        5 -> StopWithTime("4.1", "4", LocalTime.of(0, 3), "a", BUS)
        6 -> StopWithTime("3.1", "3", LocalTime.of(0, 3), "a", BUS)
        7 -> StopWithTime("5.1", "5", LocalTime.of(0, 4), "a", BUS)
        else -> StopWithTime("-100.1", "-100", LocalTime.of(0, 0), "a", BUS)
    }
}

fun listOfStopsCase2(nr: Int): StopWithTime {
    return when (nr) {
        0 -> StopWithTime("1.1", "1", LocalTime.of(0, 0), "a", BUS)
        1 -> StopWithTime("2.1", "2", LocalTime.of(0, 2), "a", BUS)
        2 -> StopWithTime("2.1", "2", LocalTime.of(0, 5), "a", BUS)
        3 -> StopWithTime("3.1", "3", LocalTime.of(0, 6), "a", BUS)
        4 -> StopWithTime("2.1", "2", LocalTime.of(0,4), "a", BUS)
        else -> StopWithTime("-100.1", "100", LocalTime.of(0, 0), "a", BUS)
    }
}

fun listOfStopsCase3(nr: Int): StopWithTime {
    return when (nr) {
        0 -> StopWithTime("1.1", "1", LocalTime.of(0, 0), "a", BUS)
        1 -> StopWithTime("3.1", "3", LocalTime.of(0, 2), "a", BUS)
        2 -> StopWithTime("3.1", "3", LocalTime.of(0, 3), "a", BUS)
        3 -> StopWithTime("4.1", "4", LocalTime.of(0, 5), "a", BUS)
        4 -> StopWithTime("4.1", "4", LocalTime.of(0,6), "a", BUS)
        5 -> StopWithTime("5.1", "5", LocalTime.of(0,7), "a", BUS)
        6 -> StopWithTime("2.1", "2", LocalTime.of(0,4), "a", BUS)
        7 -> StopWithTime("5.1", "5", LocalTime.of(0,9), "a", BUS)
        else -> StopWithTime("-100.1", "-100", LocalTime.of(0, 0), "a", BUS)
    }
}

fun main() {
/*
    // case 1
    val connections = mapOf(
        listOfStopsCase1(0) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase1(1), SimpleStopConnection(
                    1, NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase1(0), listOfStopsCase1(1))
                )
            )
        ),
        listOfStopsCase1(1) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase1(2), SimpleStopConnection(
                    CHANGE, TimeDifferenceFinder.find(listOfStopsCase1(1), listOfStopsCase1(2))
                )
            ),
            StopConnectionWithArrival(
                listOfStopsCase1(4), SimpleStopConnection(
                    1, CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase1(1), listOfStopsCase1(4))
                )
            )
        ),
        listOfStopsCase1(2) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase1(3), SimpleStopConnection(
                    3, NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase1(2), listOfStopsCase1(3))
                )
            )
        ),
        listOfStopsCase1(3) to emptyList(),
        listOfStopsCase1(4) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase1(5), SimpleStopConnection(
                    1, NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase1(4), listOfStopsCase1(5))
                )
            ),
            StopConnectionWithArrival(
                listOfStopsCase1(6), SimpleStopConnection(
                    CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase1(4), listOfStopsCase1(6))
                )
            )
        ),
        listOfStopsCase1(5) to emptyList(),
        listOfStopsCase1(6) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase1(7), SimpleStopConnection(
                    2, NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase1(6), listOfStopsCase1(7))
                )
            )
        ),
        listOfStopsCase1(7) to emptyList()
    )

    val result = ShortestPathAlgorithm(
        connections, listOfStopsCase1(0)
    ).find("4", listOf("4.1"))
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
                    1, NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase2(0), listOfStopsCase2(4))
                )
            )
        ),
        listOfStopsCase2(1) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase2(2), SimpleStopConnection(
                    2, NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase2(1), listOfStopsCase2(2))
                )
            )
        ),
        listOfStopsCase2(2) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase2(3), SimpleStopConnection(
                    2, NO_CHANGE,
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
        connections, listOfStopsCase2(0)
    ).find("3", listOf("3.1"))
    //result.forEach { (k, v) -> println("$k = $v") }
*/


    // case 3
    val connections = mapOf(
        listOfStopsCase3(0) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(1), SimpleStopConnection(
                    "1", NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase3(0), listOfStopsCase3(1))
                )
            ),
            StopConnectionWithArrival(
                listOfStopsCase3(6), SimpleStopConnection(
                    "4", NO_CHANGE,
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
                    "2", NO_CHANGE,
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
                    "3", NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase3(4), listOfStopsCase3(5))
                )
            )
        ),
        listOfStopsCase3(5) to emptyList(),
        listOfStopsCase3(6) to listOf(
            StopConnectionWithArrival(
                listOfStopsCase3(7), SimpleStopConnection(
                    "4", NO_CHANGE,
                    TimeDifferenceFinder.find(listOfStopsCase3(6), listOfStopsCase3(7))
                )
            )
        ),
        listOfStopsCase3(7) to emptyList()
    )

    ShortestPathAlgorithm(
        connections, listOfStopsCase3(0)
    ).find("5")

    //result.forEach { (k, v) -> println("$k = $v") }
//    ChronoUnit.MINUTES.between()
    //val a1 = LocalTime.of(10,55).until(LocalTime.of(10,50), ChronoUnit.MINUTES)
//    println(LocalTime.of(10,45).compareTo(LocalTime.of(10,50)))
}