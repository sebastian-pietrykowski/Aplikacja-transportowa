package com.example.backend.domain

class MinTimeStopsContainer {

    private val minTimeForStops = HashMap<Int, Pair<Stop, Int>>()
    // locationId -> (stop with min departureTime, totalTimeWithPenalty)

    fun add(stop: Stop, totalTimeWithPenalty: Int) {
        minTimeForStops.merge(stop.locationId, Pair(stop, totalTimeWithPenalty))
        { oldPair, newPair ->
            if (oldPair.second <= newPair.second)
                oldPair else newPair
        }
    }

    fun find(locationId: Int): Stop? {
        return minTimeForStops[locationId]?.first
    }
}