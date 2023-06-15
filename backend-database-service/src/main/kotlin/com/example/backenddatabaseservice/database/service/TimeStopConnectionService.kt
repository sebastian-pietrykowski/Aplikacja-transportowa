package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.repository.TimeStopConnectionRepository
import org.springframework.stereotype.Service

@Service
class TimeStopConnectionService(
    private val timeStopConnectionRepository: TimeStopConnectionRepository
) {

//    fun findByStopConnectionId(stopConnectionId: Long): List<TimeStopConnectionEntity> {
//        return timeStopConnectionRepository.findByStopConnectionId(stopConnectionId)
//    }
}