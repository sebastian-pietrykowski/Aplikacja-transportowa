package com.example.backenddatabaseservice.backend.model

data class StopConnectionWithDeparture(
    val departureStopWithTime: StopWithTime, val simpleConnection: SimpleStopConnection?
)