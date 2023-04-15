package com.example.backend.domain

data class StopConnectionWithDeparture(
    val departureStop: Stop, override val simpleConnection: SimpleStopConnection?
) : StopConnection(departureStop, simpleConnection)