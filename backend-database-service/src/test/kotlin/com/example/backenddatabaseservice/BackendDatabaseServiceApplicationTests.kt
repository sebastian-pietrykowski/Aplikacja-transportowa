package com.example.backenddatabaseservice

import com.example.backenddatabaseservice.backend.service.FromStopConnectionsFinder
import com.example.backenddatabaseservice.backend.service.ShortestPathAlgorithm
import com.example.backenddatabaseservice.database.repository.*
import com.example.backenddatabaseservice.database.service.DatabaseCleanerService
import com.example.backenddatabaseservice.database.service.DatabaseLoaderService
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
    lateinit var shortestPathAlgorithm: ShortestPathAlgorithm
    @Autowired
    lateinit var databaseCleanerService: DatabaseCleanerService
    @Autowired
    lateinit var databaseLoaderService: DatabaseLoaderService

    @Test
    fun testPath() {
        shortestPathAlgorithm.find("1.1", LocalTime.of(0,0), "5.1")
    }
    @Test
    fun loadWarsawDatabase() {
        databaseLoaderService.loadDatabase()
    }
}
