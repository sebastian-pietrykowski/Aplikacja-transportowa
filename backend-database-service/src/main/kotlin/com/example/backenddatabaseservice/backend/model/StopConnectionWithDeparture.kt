package com.example.backenddatabaseservice.backend.model

data class StopConnectionWithDeparture(
    val departureStop: Stop, override val simpleConnection: SimpleStopConnection?
) : StopConnection(departureStop, simpleConnection)