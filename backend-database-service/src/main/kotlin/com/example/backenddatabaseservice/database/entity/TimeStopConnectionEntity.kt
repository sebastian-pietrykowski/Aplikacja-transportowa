package com.example.backenddatabaseservice.database.entity

import jakarta.persistence.*
import java.time.LocalTime

@Entity @Table(name = "time_stop_connection")
class TimeStopConnectionEntity(
    @Id
    @GeneratedValue
    @Column
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stop_connection")
    val stopConnection: StopConnectionEntity,

    @Column
    val departureTime: LocalTime,

    @Column
    val arrivalTime: LocalTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is TimeStopConnectionEntity) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}