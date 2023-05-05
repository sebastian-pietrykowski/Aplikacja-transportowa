package com.example.backenddatabaseservice.backend.model

data class StopConnectionWithArrival(
    val arrivalStopWithTime: StopWithTime, override val simpleConnection: SimpleStopConnection?
) : StopConnection(arrivalStopWithTime, simpleConnection)