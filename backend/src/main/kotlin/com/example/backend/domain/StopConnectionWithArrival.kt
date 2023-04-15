package com.example.backend.domain

data class StopConnectionWithArrival(
    val arrivalStop: Stop, override val simpleConnection: SimpleStopConnection?
) : StopConnection(arrivalStop, simpleConnection)