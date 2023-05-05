package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.repository.StopComplexRepository
import org.springframework.stereotype.Service

@Service
class StopComplexService(
    private val stopComplexRepository: StopComplexRepository
) {
    fun findStopsByComplexId(stopComplexId: String): List<StopEntity> {
        return stopComplexRepository.findById(stopComplexId).get().stops?.toList() ?: emptyList()
    }
}