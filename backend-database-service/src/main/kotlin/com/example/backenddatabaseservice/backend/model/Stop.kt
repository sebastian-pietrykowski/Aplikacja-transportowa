package com.example.backenddatabaseservice.backend.model

import com.example.backenddatabaseservice.backend.model.StopType
import java.time.LocalTime

data class Stop(
    val locationId: Int, val departureTime: LocalTime, val stopType: StopType
)