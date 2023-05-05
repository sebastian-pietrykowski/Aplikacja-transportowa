package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.service.StopConnectionService
import com.example.backenddatabaseservice.backend.model.StopConnectionType.*
import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import org.springframework.stereotype.Service

@Service
class FromStopConnectionsFinder(
    private val stopConnectionService: StopConnectionService
) {
    fun find(currentStopWithTime: StopWithTime, isStart: Boolean, lastLineNumber: String?)
            : List<StopConnectionWithArrival> {
        val result = mutableListOf<StopConnectionWithArrival>()
        val connectionsEntities: List<StopConnectionEntity> = stopConnectionService
            .findByDepartureStopId(currentStopWithTime.stopId)

        for (connectionEntity in connectionsEntities) {
            val timeConnectionEntities = stopConnectionService.findTimeStopConnections(connectionEntity.id!!)
            for (timeConnectionEntity in timeConnectionEntities) {
                val departureStopEntity = connectionEntity.departureStop
                val arrivalStopEntity = connectionEntity.arrivalStop

                val stopConnectionType = determineStopConnectionType(lastLineNumber, connectionEntity, isStart)
                val connectionCondition = currentStopWithTime.departureTime == timeConnectionEntity.departureTime
                        && currentStopWithTime.direction == connectionEntity.direction
                val connection = when (connectionCondition) {
                    true -> createNormalStopConnectionWithArrival(
                        departureStopEntity, arrivalStopEntity, timeConnectionEntity,
                        connectionEntity, stopConnectionType
                    )
                    false -> createChangeOrInitialWaitingStopConnectionWithArrival(
                        currentStopWithTime, timeConnectionEntity, stopConnectionType
                    )
                }
                result.add(connection)
            }
        }
        return result
    }

    private fun determineStopConnectionType(
        lastLineNumber: String?, connectionEntity: StopConnectionEntity, isStart: Boolean
    ): StopConnectionType {
        return when (lastLineNumber) {
            connectionEntity.lineNumber -> NO_CHANGE // same lineNumber
            null -> NO_CHANGE // departure after waiting
            else -> {
                when (isStart) {
                    true -> INITIAL_WAITING // waiting at the beginning
                    false -> CHANGE // waiting
                }
            }
        }
    }

    private fun createNormalStopConnectionWithArrival(
        departureStopEntity: StopEntity, arrivalStopEntity: StopEntity,
        timeConnectionEntity: TimeStopConnectionEntity, connectionEntity: StopConnectionEntity,
        stopConnectionType: StopConnectionType
    ): StopConnectionWithArrival {
        val departureStop = StopWithTime(
            departureStopEntity.id, departureStopEntity.stopComplex.id,
            timeConnectionEntity.departureTime, connectionEntity.direction,
            departureStopEntity.stopType
        )
        val arrivalStop = StopWithTime(
            arrivalStopEntity.id, arrivalStopEntity.stopComplex.id,
            timeConnectionEntity.arrivalTime, arrivalStopEntity.stopType
        )
        return StopConnectionWithArrival(
            arrivalStop,
            SimpleStopConnection(
                connectionEntity.lineNumber, stopConnectionType,
                TimeDifferenceFinder.find(departureStop, arrivalStop)
            )
        )
    }

    private fun createChangeOrInitialWaitingStopConnectionWithArrival(
        departureStopWithTime: StopWithTime,
        timeConnectionEntity: TimeStopConnectionEntity,
        stopConnectionType: StopConnectionType
    ): StopConnectionWithArrival {
        val departureStop = StopWithTime(
            departureStopWithTime.stopId, departureStopWithTime.complexId,
            departureStopWithTime.departureTime, departureStopWithTime.stopType
        )
        val arrivalStop = StopWithTime(
            departureStopWithTime.stopId, departureStopWithTime.complexId,
            timeConnectionEntity.departureTime, departureStopWithTime.stopType
        )
        return StopConnectionWithArrival(
            arrivalStop,
            SimpleStopConnection(
                null, stopConnectionType,
                TimeDifferenceFinder.find(departureStop, arrivalStop)
            )
        )
    }
}