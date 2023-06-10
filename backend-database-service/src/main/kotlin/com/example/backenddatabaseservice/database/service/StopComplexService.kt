package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.repository.StopComplexRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Service

@Service
class StopComplexService(
    private val stopComplexRepository: StopComplexRepository
) {
    @PersistenceContext
    var entityManager: EntityManager? = null

    fun findStopsByComplexId(stopComplexId: String): List<StopEntity> {
        return stopComplexRepository.findById(stopComplexId).orElse(null)?.stops?.toList() ?: emptyList()
    }

    fun findStopConnectionIdsDepartingFromStopComplex(sourceComplexId: String): List<Long> {
        return entityManager?.createQuery(
            "SELECT departure_connection.id "
                    + "FROM StopComplexEntity stop_complex "
                    + "INNER JOIN stop_complex.stops stop "
                    + "INNER JOIN stop.departureConnections departure_connection "
                    + "WHERE stop_complex.id = :sourceComplexId "
        )?.setParameter("sourceComplexId", sourceComplexId)?.resultList?.map { it as Long }
            ?: emptyList<Long>()
    }
}