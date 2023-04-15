package com.example.backend.domain

abstract class StopConnection(
    val consideredStop: Stop, open val simpleConnection: SimpleStopConnection?
) {
}