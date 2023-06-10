package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.repository.StopConnectionRepository
import com.example.backenddatabaseservice.database.repository.StopRepository
import com.example.backenddatabaseservice.database.repository.TimeStopConnectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service

@Service
class StopService(
    private val stopRepository: StopRepository
) {

    fun findDepartureConnectionsById(stopId: String): Set<StopConnectionEntity> {
        return stopRepository.findById(stopId)?.get()?.departureConnections ?: emptySet()
    }

//    @Query("SELECT s FROM StopEntity s JOIN FETCH s.departureConnections WHERE s.id = :stopId")
//    abstract fun findByIdWithDepartureConnections(@Param("stopId") stopId: String): StopEntity?



//    fun findStopsByComplexId(complexId: String): List<StopEntity> {
//        return stopRepository.findStopsByComplexId(complexId)
//    }
}