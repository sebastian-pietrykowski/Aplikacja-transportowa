package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.service.StopConnectionService
import com.example.backenddatabaseservice.backend.model.StopConnectionType.*
import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import com.example.backenddatabaseservice.database.service.StopComplexService
import org.springframework.stereotype.Service

@Service
class FromStopConnectionsFinder(
    private val stopConnectionService: StopConnectionService,
    private val stopComplexService: StopComplexService
) {
    fun find(currentStopWithTime: StopWithTime, isStart: Boolean, lastLineNumber: String?)
            : List<StopConnectionWithArrival> {
        val result = mutableListOf<StopConnectionWithArrival>()
        val connectionsEntities: List<StopConnectionEntity> = stopConnectionService
            .findByDepartureStopId(currentStopWithTime.stopId)

        for (connectionEntity in connectionsEntities) {
            val timeConnectionEntities = stopConnectionService.findTimeStopConnectionsBeginningFrom(
                connectionEntity.id!!, currentStopWithTime.departureTime
            )
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
                        currentStopWithTime, departureStopEntity, timeConnectionEntity, stopConnectionType
                    )
                }
                result.add(connection)
            }
        }

        val neighborStopsEntities = stopComplexService.findStopsByComplexId(currentStopWithTime.complexId)
        for(neighborStopEntity in neighborStopsEntities) {
            val neighborConnectionsEntities = stopConnectionService.findByDepartureStopId(neighborStopEntity.id)
            for (neighborConnectionEntity in neighborConnectionsEntities) {
                val neighborTimeConnectionsEntities = stopConnectionService
                    .findTimeStopConnectionsBeginningFrom(
                        neighborConnectionEntity.id!!, currentStopWithTime.departureTime
                    )
                for (neighborTimeConnectionEntity in neighborTimeConnectionsEntities) {
                    val neighborDepartureStopEntity = neighborConnectionEntity.departureStop
                    val neighborStopConnectionType = determineStopConnectionType(
                        lastLineNumber, neighborConnectionEntity, isStart
                    )
                    val neighborConnection = createChangeOrInitialWaitingStopConnectionWithArrival(
                        currentStopWithTime, neighborDepartureStopEntity, neighborTimeConnectionEntity,
                        neighborStopConnectionType
                    )
                    result.add(neighborConnection)
                }
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
        departureStopWithTime: StopWithTime, departureStopEntity: StopEntity,
        timeConnectionEntity: TimeStopConnectionEntity,
        stopConnectionType: StopConnectionType
    ): StopConnectionWithArrival {
        val departureStop = StopWithTime(
            departureStopWithTime.stopId, departureStopWithTime.complexId,
            departureStopWithTime.departureTime, departureStopWithTime.stopType
        )
        val arrivalStop = StopWithTime(
            departureStopEntity.id, departureStopEntity.stopComplex.id,
            timeConnectionEntity.departureTime, departureStopEntity.stopType
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