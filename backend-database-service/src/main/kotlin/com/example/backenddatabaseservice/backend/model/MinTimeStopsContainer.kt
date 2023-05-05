package com.example.backenddatabaseservice.backend.model

class MinTimeStopsContainer {

    private val minTimeForStops = HashMap<String, Pair<StopWithTime, Int>>()
    // stopId -> (stop with min departureTime, totalTimeWithPenalty)

    fun add(stopWithTime: StopWithTime, totalTimeWithPenalty: Int) {
        minTimeForStops.merge(stopWithTime.stopId, Pair(stopWithTime, totalTimeWithPenalty))
        { oldPair, newPair ->
            if (oldPair.second <= newPair.second)
                oldPair else newPair
        }
    }

    fun find(stopsInComplex: List<String>): StopWithTime? {
        //return minTimeForStops[complexId]?.first
        return stopsInComplex.mapNotNull { stopId -> minTimeForStops[stopId] }
            .minByOrNull { it.second }?.first
    }
}