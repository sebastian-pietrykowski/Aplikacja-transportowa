package com.example.backenddatabaseservice

import com.example.backenddatabaseservice.backend.model.StopType.*
import com.example.backenddatabaseservice.backend.model.StopWithTime
import com.example.backenddatabaseservice.backend.service.FromStopConnectionsFinder
import com.example.backenddatabaseservice.backend.service.ShortestPathAlgorithmSpring
import com.example.backenddatabaseservice.database.entity.*
import com.example.backenddatabaseservice.database.repository.*
import com.example.backenddatabaseservice.database.service.StopConnectionService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalTime

@SpringBootTest
class BackendDatabaseServiceApplicationTests {

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
    lateinit var shortestPathAlgorithmSpring: ShortestPathAlgorithmSpring

    @Test
    fun contextLoads() {
        assert(true)
    }

/*
    @Test
    fun testRepository() {
        val locality1 = LocalityEntity("WA", "Warszawa", null)
        localityRepository.save(locality1)

        val stopComplex1 = StopComplexEntity("0001", "One", locality1, null)
        stopComplexRepository.save(stopComplex1)
        val stopComplex2 = StopComplexEntity("0002", "Two", locality1, null)
        stopComplexRepository.save(stopComplex2)
        val stopComplex3 = StopComplexEntity("0003", "Three", locality1, null)
        stopComplexRepository.save(stopComplex3)
        val stopComplex4 = StopComplexEntity("0004", "Four", locality1, null)
        stopComplexRepository.save(stopComplex4)
        val stopComplex5 = StopComplexEntity("0005", "Five", locality1, null)
        stopComplexRepository.save(stopComplex5)

        val stopComplexTmp = StopComplexEntity("0006", "Tmp", locality1, null)
        stopComplexRepository.save(stopComplexTmp)

        val stop1 = StopEntity("000101", stopComplex1, BUS, 0.0, 0.0)
        stopRepository.save(stop1)
        val stop2 = StopEntity("000201", stopComplex2, BUS, 0.0, 0.0)
        stopRepository.save(stop2)
        val stop3_1 = StopEntity("000301", stopComplex3, BUS, 0.0, 0.0)
        stopRepository.save(stop3_1)
        val stop3_2 = StopEntity("000302", stopComplex3, BUS, 0.0, 0.0)
        stopRepository.save(stop3_2)
        val stop4 = StopEntity("000401", stopComplex4, BUS, 0.0, 0.0)
        stopRepository.save(stop4)
        val stop5 = StopEntity("000501", stopComplex5, BUS, 0.0, 0.0)
        stopRepository.save(stop5)

        val stopTmp = StopEntity("000601", stopComplexTmp, BUS, 0.0, 0.0)
        stopRepository.save(stopTmp)

        val stopConnection1 = StopConnectionEntity(
            null, stop1, "1", "-", stop3_1,
            LocalTime.of(0, 1), LocalTime.of(0, 1), null
        )
        stopConnectionRepository.save(stopConnection1)
        val stopConnection2 = StopConnectionEntity(
            null, stop1, "4", "-", stop2,
            LocalTime.of(0, 1), LocalTime.of(0, 1), null
        )
        stopConnectionRepository.save(stopConnection2)
        val stopConnection3 = StopConnectionEntity(
            null, stop2, "4", "-", stop5,
            LocalTime.of(0, 1), LocalTime.of(0, 1), null
        )
        stopConnectionRepository.save(stopConnection3)
        val stopConnection4 = StopConnectionEntity(
            null, stop3_2, "2", "-", stop4,
            LocalTime.of(0, 1), LocalTime.of(0, 1), null
        )
        stopConnectionRepository.save(stopConnection4)
        val stopConnection5 = StopConnectionEntity(
            null, stop4, "3", "-", stop5,
            LocalTime.of(0, 1), LocalTime.of(0, 1), null
        )
        stopConnectionRepository.save(stopConnection5)

        val stopConnectionTmp = StopConnectionEntity(
            null, stop3_2, "10", "-", stopTmp,
            LocalTime.of(0, 1), LocalTime.of(0, 1), null
        )
        stopConnectionRepository.save(stopConnectionTmp)

        val time1 = TimeStopConnectionEntity(
            null, stopConnection1,
            LocalTime.of(0, 0), LocalTime.of(0, 2)
        )
        timeStopConnectionRepository.save(time1)
        val time2 = TimeStopConnectionEntity(
            null, stopConnection2,
            LocalTime.of(0, 0), LocalTime.of(0, 4)
        )
        timeStopConnectionRepository.save(time2)
        val time3 = TimeStopConnectionEntity(
            null, stopConnection3,
            LocalTime.of(0, 4), LocalTime.of(0, 9)
        )
        timeStopConnectionRepository.save(time3)
        val time4 = TimeStopConnectionEntity(
            null, stopConnection4,
            LocalTime.of(0, 3), LocalTime.of(0, 5)
        )
        timeStopConnectionRepository.save(time4)
        val time5 = TimeStopConnectionEntity(
            null, stopConnection5,
            LocalTime.of(0, 6), LocalTime.of(0, 7)
        )
        timeStopConnectionRepository.save(time5)

        val timeTmp = TimeStopConnectionEntity(
            null, stopConnectionTmp,
            LocalTime.of(0, 2), LocalTime.of(0, 3)
        )
        timeStopConnectionRepository.save(timeTmp)


        assert(true)
    }
*/

    @Test
    fun test1() {
        //assert (stopConnectionService.findByDepartureStopId("000101").isNotEmpty())
        //assert(stopConnectionRepository.findByDepartureStopId("000101").isNotEmpty())
        //stopConnectionRepository.findByDepartureStopId("000101")

        val startStopWithTime = StopWithTime("000101", "0001", LocalTime.of(0,0), BUS)
//        val connections = connectionFinder.find(startStopWithTime, true, null)
//        println("\n------------")
//        println(connections.size)
//        println(connections)
//        connections.forEach { c -> println(c.arrivalStopWithTime.stopId) }
//        println("------------")
//        //assert(true)
        shortestPathAlgorithmSpring.find(startStopWithTime, "0005")
    }

}
