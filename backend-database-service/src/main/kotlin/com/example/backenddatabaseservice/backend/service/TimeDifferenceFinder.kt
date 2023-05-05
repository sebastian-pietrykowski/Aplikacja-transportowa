package com.example.backenddatabaseservice.backend.service

import com.example.backenddatabaseservice.backend.model.StopWithTime
import java.time.temporal.ChronoUnit

class TimeDifferenceFinder {
    companion object {
        fun find(departureStopWithTime: StopWithTime, arrivalStopWithTime: StopWithTime): Int {
            val departureTime = departureStopWithTime.departureTime
            val arrivalTime = arrivalStopWithTime.departureTime
            val timeDifference = departureTime.until(arrivalTime, ChronoUnit.MINUTES)
            return timeDifference.toInt()
        }
    }
}