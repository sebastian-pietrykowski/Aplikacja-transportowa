package com.example.backenddatabaseservice.backend.model

import java.time.LocalTime

data class StopWithTime(
    val stopId: String, val complexId: String, val departureTime: LocalTime,
    val direction: String
) {
    constructor(
        stopId: String, complexId: String, departureTime: LocalTime
    ) : this(stopId, complexId, departureTime, "-")
}