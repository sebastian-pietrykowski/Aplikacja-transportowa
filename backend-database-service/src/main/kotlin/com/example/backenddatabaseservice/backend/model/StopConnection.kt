package com.example.backenddatabaseservice.backend.model

abstract class StopConnection(
    val consideredStop: Stop, open val simpleConnection: SimpleStopConnection?
) {
}