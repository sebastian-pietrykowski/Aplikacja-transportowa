package com.example.backenddatabaseservice.backend.model

abstract class StopConnection(
    val consideredStopWithTime: StopWithTime, open val simpleConnection: SimpleStopConnection?
)