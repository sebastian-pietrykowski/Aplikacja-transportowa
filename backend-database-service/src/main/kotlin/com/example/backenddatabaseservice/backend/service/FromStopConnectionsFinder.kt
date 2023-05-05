package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.*
import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.service.StopConnectionService
import com.example.backenddatabaseservice.database.service.TimeStopConnectionService
import com.example.backenddatabaseservice.backend.model.StopConnectionType.*
import org.springframework.stereotype.Service

@Service
class FromStopConnectionsFinder(
    private val stopConnectionService: StopConnectionService,
    //private val timeStopConnectionService: TimeStopConnectionService
) {
    //Map<Stop, List<StopConnectionWithArrival>>

    fun find(currentStopWithTime: StopWithTime): List<StopConnectionWithArrival> {
        val result = emptyList<StopConnectionWithArrival>()

        val actualTime = currentStopWithTime.departureTime
        val connectionsEntities: List<StopConnectionEntity> = stopConnectionService.findByDepartureStopId("000101")
        for (connectionEntity in connectionsEntities) {
            val timeConnectionEntities = stopConnectionService.findTimeStopConnections(connectionEntity.id!!)
            for (timeConnectionEntity in timeConnectionEntities) {
                val departureStop = connectionEntity.departureStop
//                val stopConnectionType = stop
//                val connection = StopConnectionWithArrival(
//                    StopWithTime(
//                        departureStop.id, departureStop.stopComplex.id,
//                        timeConnectionEntity.departureTime, departureStop.stopType
//                    ),
//                    SimpleStopConnection(
//                        connectionEntity.lineNumber
//                    )
//                )
            }
        }
//        timeStopConnectionService.findByStopConnectionId(1L)
        return emptyList()
    }
}