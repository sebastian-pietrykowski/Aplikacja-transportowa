package com.example.backenddatabaseservice.backend.model

import java.time.LocalTime

data class StopWithTime(
    val stopId: String, val complexId: String, val departureTime: LocalTime, val stopType: StopType
)