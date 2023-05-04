package com.example.backenddatabaseservice.database.entity

import java.time.LocalTime
import jakarta.persistence.*

@Entity @Table(name = "stop_connection")
class StopConnectionEntity(
    @EmbeddedId
    val id: StopConnectionId,

    @Column
    val direction: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("departureStopId")
    @JoinColumn(name = "departure_stop_id")
    val departureStop: StopEntity,

    @ManyToOne
    @JoinColumn(name = "arrival_stop_id", nullable = true)
    val arrivalStop: StopEntity?,

    @Column
    val minTransitTime: LocalTime,

    @Column
    val maxTransitTime: LocalTime,

    @OneToMany(mappedBy = "stopConnection")
    val timeDepartures: Set<TimeDepartureEntity> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is StopConnectionEntity) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}