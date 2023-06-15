package com.example.backenddatabaseservice

import com.example.backenddatabaseservice.backend.model.TransportMode
import com.example.backenddatabaseservice.backend.service.FromStopConnectionsFinder
import com.example.backenddatabaseservice.backend.service.ShortestPathAlgorithm
import com.example.backenddatabaseservice.database.entity.*
import com.example.backenddatabaseservice.database.repository.*
import com.example.backenddatabaseservice.database.service.DatabaseCleanerService
import com.example.backenddatabaseservice.database.service.StopConnectionService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalTime

@SpringBootTest
class TestDatabases {

    @Autowired
    lateinit var localityRepository: LocalityRepository
    @Autowired
    lateinit var stopComplexRepository: StopComplexRepository
    @Autowired
    lateinit var stopRepository: StopRepository
    @Autowired
    lateinit var stopConnectionRepository: StopConnectionRepository
    @Autowired
    lateinit var timeStopConnectionRepository: TimeStopConnectionRepository
    @Autowired
    lateinit var stopConnectionService: StopConnectionService
    @Autowired
    lateinit var connectionFinder: FromStopConnectionsFinder
    @Autowired
    lateinit var shortestPathAlgorithm: ShortestPathAlgorithm
    @Autowired
    lateinit var databaseCleanerService: DatabaseCleanerService

    @Test
    fun clearDatabase() {
        databaseCleanerService.deleteAll()
    }

    @Test
    fun addToDatabaseRouteWithoutChanges() {
        val locality1 = LocalityEntity("AB", "Some City", null)
        localityRepository.save(locality1)

        val stopComplex1 = StopComplexEntity("1", "One", locality1, null)
        stopComplexRepository.save(stopComplex1)
        val stopComplex2 = StopComplexEntity("2", "Two", locality1, null)
        stopComplexRepository.save(stopComplex2)
        val stopComplex3 = StopComplexEntity("3", "Three", locality1, null)
        stopComplexRepository.save(stopComplex3)
        val stopComplex4 = StopComplexEntity("4", "Four", locality1, null)
        stopComplexRepository.save(stopComplex4)

        val stop11 = StopEntity("1.1", stopComplex1, 0.0, 0.0)
        stopRepository.save(stop11)
        val stop21 = StopEntity("2.1", stopComplex2, 0.0, 0.0)
        stopRepository.save(stop21)
        val stop31 = StopEntity("3.1", stopComplex3, 0.0, 0.0)
        stopRepository.save(stop31)
        val stop41 = StopEntity("4.1", stopComplex4, 0.0, 0.0)
        stopRepository.save(stop41)

        val stopConnection1 = StopConnectionEntity(
            null, stop11, "1", TransportMode.BUS, "-", stop21,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection1)
        val stopConnection2 = StopConnectionEntity(
            null, stop21, "1", TransportMode.BUS, "-", stop31,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection2)
        val stopConnection3 = StopConnectionEntity(
            null, stop31, "1", TransportMode.BUS, "-", stop41,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection3)

        val time1 = TimeStopConnectionEntity(
            null, stopConnection1,
            LocalTime.of(0, 0), LocalTime.of(0, 2)
        )
        timeStopConnectionRepository.save(time1)
        val time2 = TimeStopConnectionEntity(
            null, stopConnection2,
            LocalTime.of(0, 2), LocalTime.of(0, 3)
        )
        timeStopConnectionRepository.save(time2)
        val time3 = TimeStopConnectionEntity(
            null, stopConnection3,
            LocalTime.of(0, 3), LocalTime.of(0, 6)
        )
        timeStopConnectionRepository.save(time3)
    }

    @Test
    fun addToDatabaseRouteWithChange() {
        val locality1 = LocalityEntity("CV", "Some City 2", null)
        localityRepository.save(locality1)

        val stopComplex1 = StopComplexEntity("1", "One", locality1, null)
        stopComplexRepository.save(stopComplex1)
        val stopComplex2 = StopComplexEntity("2", "Two", locality1, null)
        stopComplexRepository.save(stopComplex2)
        val stopComplex3 = StopComplexEntity("3", "Three", locality1, null)
        stopComplexRepository.save(stopComplex3)

        val stop11 = StopEntity("1.1", stopComplex1, 0.0, 0.0)
        stopRepository.save(stop11)
        val stop21 = StopEntity("2.1", stopComplex2, 0.0, 0.0)
        stopRepository.save(stop21)
        val stop31 = StopEntity("3.1", stopComplex3, 0.0, 0.0)
        stopRepository.save(stop31)

        val stopConnection1 = StopConnectionEntity(
            null, stop11, "5", TransportMode.TRAM, "-", stop21,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection1)
        val stopConnection2 = StopConnectionEntity(
            null, stop21, "10", TransportMode.TRAM, "-", stop31,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection2)

        val time1 = TimeStopConnectionEntity(
            null, stopConnection1,
            LocalTime.of(8, 50), LocalTime.of(8, 53)
        )
        timeStopConnectionRepository.save(time1)
        val time2 = TimeStopConnectionEntity(
            null, stopConnection2,
            LocalTime.of(8, 58), LocalTime.of(9, 0)
        )
        timeStopConnectionRepository.save(time2)
    }

    @Test
    fun addToDatabaseRouteOneLineStartingLaterOrTwoLinesStartingEarlier() {
        val locality1 = LocalityEntity("CV", "Some City 2", null)
        localityRepository.save(locality1)

        val stopComplex1 = StopComplexEntity("1", "One", locality1, null)
        stopComplexRepository.save(stopComplex1)
        val stopComplex2 = StopComplexEntity("2", "Two", locality1, null)
        stopComplexRepository.save(stopComplex2)
        val stopComplex3 = StopComplexEntity("3", "Three", locality1, null)
        stopComplexRepository.save(stopComplex3)

        val stop11 = StopEntity("1.1", stopComplex1, 0.0, 0.0)
        stopRepository.save(stop11)
        val stop21 = StopEntity("2.1", stopComplex2, 0.0, 0.0)
        stopRepository.save(stop21)
        val stop31 = StopEntity("3.1", stopComplex3, 0.0, 0.0)
        stopRepository.save(stop31)

        val stopConnection1 = StopConnectionEntity(
            null, stop11, "2", TransportMode.BUS, "-", stop21,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection1)
        val stopConnection2 = StopConnectionEntity(
            null, stop11, "1", TransportMode.BUS, "-", stop21,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection2)
        val stopConnection3 = StopConnectionEntity(
            null, stop21, "2", TransportMode.BUS, "-", stop31,
            LocalTime.of(0, 1), LocalTime.of(0, 5), null
        )
        stopConnectionRepository.save(stopConnection3)

        val time1 = TimeStopConnectionEntity(
            null, stopConnection1,
            LocalTime.of(0, 2), LocalTime.of(0, 5)
        )
        timeStopConnectionRepository.save(time1)
        val time2 = TimeStopConnectionEntity(
            null, stopConnection2,
            LocalTime.of(0, 0), LocalTime.of(0, 3)
        )
        timeStopConnectionRepository.save(time2)
        val time3 = TimeStopConnectionEntity(
            null, stopConnection3,
            LocalTime.of(0, 5), LocalTime.of(0, 6)
        )
        timeStopConnectionRepository.save(time3)
    }

    @Test
    fun addToDatabaseRouteThreeLinesFasterOrOneLineLater() {
        val locality1 = LocalityEntity("CD", "Some City 5", null)
        localityRepository.save(locality1)

        val stopComplex1 = StopComplexEntity("1", "One", locality1, null)
        stopComplexRepository.save(stopComplex1)
        val stopComplex2 = StopComplexEntity("2", "Two", locality1, null)
        stopComplexRepository.save(stopComplex2)
        val stopComplex3 = StopComplexEntity("3", "Three", locality1, null)
        stopComplexRepository.save(stopComplex3)
        val stopComplex4 = StopComplexEntity("4", "Four", locality1, null)
        stopComplexRepository.save(stopComplex4)
        val stopComplex5 = StopComplexEntity("5", "Five", locality1, null)
        stopComplexRepository.save(stopComplex5)

        val stop11 = StopEntity("1.1", stopComplex1, 0.0, 0.0)
        stopRepository.save(stop11)
        val stop21 = StopEntity("2.1", stopComplex2, 0.0, 0.0)
        stopRepository.save(stop21)
        val stop31 = StopEntity("3.1", stopComplex3, 0.0, 0.0)
        stopRepository.save(stop31)
        val stop41 = StopEntity("4.1", stopComplex4, 0.0, 0.0)
        stopRepository.save(stop41)
        val stop51 = StopEntity("5.1", stopComplex5, 0.0, 0.0)
        stopRepository.save(stop51)

        val stopConnection11to21 = StopConnectionEntity(
            null, stop11, "1", TransportMode.BUS, "-", stop21,
            null, null
        )
        stopConnectionRepository.save(stopConnection11to21)
        val stopConnection21to31 = StopConnectionEntity(
            null, stop21, "2", TransportMode.BUS, "-", stop31,
            null, null
        )
        stopConnectionRepository.save(stopConnection21to31)
        val stopConnection31to41 = StopConnectionEntity(
            null, stop31, "3", TransportMode.BUS, "-", stop41,
            null, null
        )
        stopConnectionRepository.save(stopConnection31to41)
        val stopConnection11to51 = StopConnectionEntity(
            null, stop11, "4", TransportMode.BUS, "-", stop51,
            null, null
        )
        stopConnectionRepository.save(stopConnection11to51)
        val stopConnection51to41 = StopConnectionEntity(
            null, stop51, "4", TransportMode.BUS, "-", stop41,
            null, null
        )
        stopConnectionRepository.save(stopConnection51to41)

        val time11to21 = TimeStopConnectionEntity(
            null, stopConnection11to21,
            LocalTime.of(0, 0), LocalTime.of(0, 2)
        )
        timeStopConnectionRepository.save(time11to21)
        val time21to31 = TimeStopConnectionEntity(
            null, stopConnection21to31,
            LocalTime.of(0, 3), LocalTime.of(0, 5)
        )
        timeStopConnectionRepository.save(time21to31)
        val time31to41 = TimeStopConnectionEntity(
            null, stopConnection31to41,
            LocalTime.of(0, 6), LocalTime.of(0, 7)
        )
        timeStopConnectionRepository.save(time31to41)
        val time11to51 = TimeStopConnectionEntity(
            null, stopConnection11to51,
            LocalTime.of(0, 0), LocalTime.of(0, 4)
        )
        timeStopConnectionRepository.save(time11to51)
        val time51to41 = TimeStopConnectionEntity(
            null, stopConnection51to41,
            LocalTime.of(0, 4), LocalTime.of(0, 9)
        )
        timeStopConnectionRepository.save(time51to41)
    }
}