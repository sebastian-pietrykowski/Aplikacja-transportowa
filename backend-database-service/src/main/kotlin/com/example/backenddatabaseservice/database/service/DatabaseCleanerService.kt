package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.repository.LocalityRepository
import com.example.backenddatabaseservice.database.repository.StopComplexRepository
import com.example.backenddatabaseservice.database.repository.StopRepository
import com.example.backenddatabaseservice.database.repository.TimeStopConnectionRepository
import org.springframework.stereotype.Service

@Service
class DatabaseCleanerService(
    private val localityRepository: LocalityRepository,
    private val stopComplexRepository: StopComplexRepository,
    private val stopConnectionRepository: TimeStopConnectionRepository,
    private val stopRepository: StopRepository,
    private val timeStopConnectionRepository: TimeStopConnectionRepository
) {
    fun deleteAll() {
        localityRepository.deleteAll()
        stopComplexRepository.deleteAll()
        stopRepository.deleteAll()
        stopConnectionRepository.deleteAll()
        timeStopConnectionRepository.deleteAll()
    }
}