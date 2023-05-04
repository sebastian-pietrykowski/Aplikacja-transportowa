package com.example.backenddatabaseservice.backend.model

import com.example.backenddatabaseservice.backend.model.StopConnectionType.*

data class SimpleStopConnection(
    val lineNumber: Int?, val type: StopConnectionType,
    val realTimeDifference: Int, val timeDifferenceWithPenalty: Int
) {
    constructor(type: StopConnectionType, realTimeDifference: Int)
            : this(
        null, type, realTimeDifference,
        calculateRealTime(realTimeDifference, type)
    )

    constructor(lineNumber: Int?, type: StopConnectionType, realTimeDifference: Int)
            : this(
        lineNumber, type, realTimeDifference,
        calculateRealTime(realTimeDifference, type)
    )

    companion object {
        private fun calculateRealTime(realTimeDifference: Int, type: StopConnectionType): Int {
            return when (type) {
                INITIAL_LINE -> realTimeDifference
                INITIAL_WAITING -> realTimeDifference
                SAME_LINE -> realTimeDifference
                CHANGE -> realTimeDifference + Constraints.PENALTY_FOR_CHANGE
            }
        }
    }
}