package com.example.backend.domain

import java.time.temporal.ChronoUnit

data class StopEdge (val stop: Stop, val timeDifference: Int) {
    constructor(departureStop: Stop, arrivalStop: Stop) : this(
        arrivalStop, calculateTimeDifference(departureStop, arrivalStop))

    companion object {
        fun calculateTimeDifference(departureStop: Stop, arrivalStop: Stop): Int {
            val departureTime = departureStop.departureTime
            val arrivalTime = arrivalStop.departureTime
            val timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES)
            return timeDifference.toInt()
        }
    }
}