package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.repository.StopRepository
import org.springframework.stereotype.Service

@Service
class StopService(
    private val stopRepository: StopRepository
) {

    fun findDepartureConnectionsById(stopId: String): Set<StopConnectionEntity> {
        return stopRepository.findById(stopId)?.get()?.departureConnections ?: emptySet()
    }
}