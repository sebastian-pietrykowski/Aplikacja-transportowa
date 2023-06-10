package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.service.StopConnectionService
import com.example.backenddatabaseservice.backend.model.StopConnectionType.*
import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import com.example.backenddatabaseservice.database.service.StopComplexService
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class FromStopConnectionsFinder(
    private val stopConnectionService: StopConnectionService,
    private val stopComplexService: StopComplexService
) {
    fun find(currentStopWithTime: StopWithTime, isStart: Boolean, lastLineNumber: String?)
            : List<StopConnectionWithArrival> {
        val result = mutableListOf<StopConnectionWithArrival>()

        // add connections incoming from the current stop
        val connectionsEntities: List<StopConnectionEntity> = stopConnectionService
            .findByDepartureStopId(currentStopWithTime.stopId)
        addTimeConnections(connectionsEntities, result, currentStopWithTime, lastLineNumber, isStart)

        // add connections incoming from stops in the same complex as the current stop
        val neighborStopsEntities = stopComplexService.findStopsByComplexId(currentStopWithTime.complexId)
        for (neighborStopEntity in neighborStopsEntities) {
            val neighborConnectionsEntities = stopConnectionService.findByDepartureStopId(neighborStopEntity.id)
            addTimeConnections(neighborConnectionsEntities, result, currentStopWithTime, lastLineNumber, isStart)
        }

        return result
    }

    private fun addTimeConnections(
        connectionsEntities: List<StopConnectionEntity>, result: MutableList<StopConnectionWithArrival>,
        departureStopWithTime: StopWithTime, lastLineNumber: String?, isStart: Boolean
    ) {
        for (connectionEntity in connectionsEntities) {
            val minDepartureTimeToSearch = determineMinDepartureTimeToSearch(
                departureStopWithTime, connectionEntity
            )
            val timeConnectionEntities = stopConnectionService.findTimeStopConnectionsBeginningFrom(
                connectionEntity.id!!, minDepartureTimeToSearch
            )
            for (timeConnectionEntity in timeConnectionEntities) {
                val departureStopEntity = connectionEntity.departureStop
                val arrivalStopEntity = connectionEntity.arrivalStop

                val stopConnectionType = determineStopConnectionType(lastLineNumber, connectionEntity, isStart)
                val connectionCondition = departureStopWithTime.departureTime == timeConnectionEntity.departureTime
                        && departureStopWithTime.direction == connectionEntity.direction
                        && (lastLineNumber == null || lastLineNumber == connectionEntity.lineNumber)
                val connection = when (connectionCondition) {
                    true -> createNormalStopConnectionWithArrival(
                        departureStopEntity, arrivalStopEntity, timeConnectionEntity,
                        connectionEntity, stopConnectionType
                    )
                    false -> createChangeOrInitialWaitingStopConnectionWithArrival(
                        departureStopWithTime, departureStopEntity, timeConnectionEntity, stopConnectionType
                    )
                }
                result.add(connection)
            }
        }
    }

    private fun determineMinDepartureTimeToSearch(
        departureStopWithTime: StopWithTime, connectionEntity: StopConnectionEntity
    ): LocalTime {
        return when (
            (departureStopWithTime.stopId == connectionEntity.departureStop.id)
        ) {
            true -> departureStopWithTime.departureTime
            false -> departureStopWithTime.departureTime.plusMinutes(
                Constraints.MIN_TIME_FOR_CHANGE_IN_MINUTES.toLong()
            )
        }
    }

    private fun determineStopConnectionType(
        lastLineNumber: String?, connectionEntity: StopConnectionEntity, isStart: Boolean
    ): StopConnectionType {
        return when (lastLineNumber) {
            connectionEntity.lineNumber -> NO_CHANGE // the same line
            null -> NO_CHANGE // departure after waiting or walking to the stop
            else -> {
                when (isStart) {
                    true -> INITIAL_WAITING // waiting at the beginning
                    false -> CHANGE // departure indicating waiting or walking
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