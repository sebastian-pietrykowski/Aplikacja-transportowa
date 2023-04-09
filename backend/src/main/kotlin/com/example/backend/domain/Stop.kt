package com.example.backend.domain

import java.time.LocalTime

data class Stop (
    val locationId: Int, val departureTime: LocalTime, val lineNumber: Int, val stopType: StopType)