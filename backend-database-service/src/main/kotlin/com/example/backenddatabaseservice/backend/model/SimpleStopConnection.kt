package com.example.backenddatabaseservice.backend.model

import com.example.backenddatabaseservice.backend.model.StopConnectionType.*

data class SimpleStopConnection(
    val lineNumber: String?, val type: StopConnectionType,
    val realTimeDifference: Int, val timeDifferenceWithPenalty: Int
) {
    constructor(type: StopConnectionType, realTimeDifference: Int)
            : this(
        null, type, realTimeDifference,
        calculateRealTime(realTimeDifference, type)
    )

    constructor(lineNumber: String?, type: StopConnectionType, realTimeDifference: Int)
            : this(
        lineNumber, type, realTimeDifference,
        calculateRealTime(realTimeDifference, type)
    )

    companion object {
        private fun calculateRealTime(realTimeDifference: Int, type: StopConnectionType): Int {
            return when (type) {
                NO_CHANGE -> realTimeDifference
                INITIAL_WAITING -> realTimeDifference
                NO_CHANGE -> realTimeDifference
                CHANGE -> realTimeDifference + Constraints.PENALTY_FOR_CHANGE_IN_MINUTES
            }
        }
    }
}