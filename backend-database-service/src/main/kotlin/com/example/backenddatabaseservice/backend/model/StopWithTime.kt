package com.example.backenddatabaseservice.backend.model

import java.time.LocalTime

data class StopWithTime(
    val stopId: String, val complexId: String, val departureTime: LocalTime,
    val direction: String, val stopType: StopType
) {
    constructor(
        stopId: String, complexId: String, departureTime: LocalTime, stopType: StopType
    ) : this(stopId, complexId, departureTime, "-", stopType)
}