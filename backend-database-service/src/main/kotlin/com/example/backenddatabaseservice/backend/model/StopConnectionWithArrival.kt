package com.example.backenddatabaseservice.backend.model

data class StopConnectionWithArrival(
    val arrivalStop: Stop, override val simpleConnection: SimpleStopConnection?
) : StopConnection(arrivalStop, simpleConnection)