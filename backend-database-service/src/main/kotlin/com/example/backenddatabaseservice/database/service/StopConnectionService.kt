package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import com.example.backenddatabaseservice.database.repository.StopConnectionRepository
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class StopConnectionService(
    private val stopConnectionRepository: StopConnectionRepository
) {

    fun findByObject(stopConnection: StopConnectionEntity): StopConnectionEntity? {
        return stopConnectionRepository.findOne(Example.of(stopConnection)).orElse(null)
    }

    fun save(stopConnection: StopConnectionEntity) {
        val that = stopConnectionRepository.findOne(Example.of(stopConnection)).orElse(null)

        if (that != null) stopConnectionRepository.delete(that)
        stopConnectionRepository.save(stopConnection)
    }

    fun findByDepartureStopId(stopId: String): List<StopConnectionEntity> {
        return stopConnectionRepository.findByDepartureStopId(stopId)
    }

    fun findTimeStopConnections(id: Long): List<TimeStopConnectionEntity> {
        return stopConnectionRepository.findById(id).get().timeStopConnections?.toList() ?: emptyList()
    }

    fun findTimeStopConnectionsBeginningFrom(id: Long, currentTime: LocalTime): List<TimeStopConnectionEntity> {
        return stopConnectionRepository.findById(id).get().timeStopConnections
            ?.filter { it.departureTime == currentTime || it.departureTime.isAfter(currentTime) }
            ?.toList() ?: emptyList()
    }

}