package com.example.backenddatabaseservice.backend.model

data class StopConnectionWithDeparture(
    val departureStopWithTime: StopWithTime, override val simpleConnection: SimpleStopConnection?
) : StopConnection(departureStopWithTime, simpleConnection)