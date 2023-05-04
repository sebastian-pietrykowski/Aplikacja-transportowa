package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.Stop
import java.time.temporal.ChronoUnit

class TimeDifferenceFinder {
    companion object {
        fun find(departureStop: Stop, arrivalStop: Stop): Int {
            val departureTime = departureStop.departureTime
            val arrivalTime = arrivalStop.departureTime
            val timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES)
            return timeDifference.toInt()
        }
    }
}