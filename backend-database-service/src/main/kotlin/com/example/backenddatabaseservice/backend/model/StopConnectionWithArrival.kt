package com.example.backenddatabaseservice.backend.model

data class StopConnectionWithArrival(
    val arrivalStopWithTime: StopWithTime, val simpleConnection: SimpleStopConnection?
)