package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import com.example.backenddatabaseservice.database.repository.StopComplexRepository
import com.example.backenddatabaseservice.database.repository.TimeStopConnectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TimeStopConnectionService(
    private val timeStopConnectionRepository: TimeStopConnectionRepository
) {

//    fun findByStopConnectionId(stopConnectionId: Long): List<TimeStopConnectionEntity> {
//        return timeStopConnectionRepository.findByStopConnectionId(stopConnectionId)
//    }


//    fun getLength(): Long {
//        return timeStopConnectionRepository.countById()
//    }
}