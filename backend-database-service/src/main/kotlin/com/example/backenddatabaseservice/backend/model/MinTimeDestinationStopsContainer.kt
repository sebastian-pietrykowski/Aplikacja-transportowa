package com.example.backenddatabaseservice.backend.model

class MinTimeDestinationStopsContainer(private val destinationComplexId: String) {

    private val minTimeForStops = HashMap<String, Pair<StopWithTime, Int>>()
    // stopId -> (stop with min departureTime, totalTimeWithPenalty)

    fun add(stopWithTime: StopWithTime, totalTimeWithPenalty: Int) {
        if (stopWithTime.complexId != destinationComplexId) return
        minTimeForStops.merge(stopWithTime.stopId, Pair(stopWithTime, totalTimeWithPenalty))
        { oldPair, newPair ->
            if (oldPair.second <= newPair.second)
                oldPair else newPair
        }
    }

    fun getMin(): StopWithTime? {
        //return minTimeForStops[complexId]?.first
        return minTimeForStops.minBy { it.value.second }.value.first
//        stopsInComplex.mapNotNull { stopId -> minTimeForStops[stopId] }
//            .minByOrNull { it.second }?.first
    }
}