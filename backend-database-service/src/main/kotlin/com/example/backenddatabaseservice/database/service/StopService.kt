package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.repository.StopConnectionRepository
import com.example.backenddatabaseservice.database.repository.StopRepository
import com.example.backenddatabaseservice.database.repository.TimeStopConnectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StopService(
    private val stopRepository: StopRepository
) {

//    fun findStopsByComplexId(complexId: String): List<StopEntity> {
//        return stopRepository.findStopsByComplexId(complexId)
//    }
}