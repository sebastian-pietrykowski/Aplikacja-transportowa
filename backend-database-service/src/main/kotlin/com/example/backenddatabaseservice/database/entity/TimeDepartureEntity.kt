package com.example.backenddatabaseservice.database.entity

import jakarta.persistence.*
import java.time.LocalTime

@Entity @Table(name = "time_departure")
class TimeDepartureEntity(
    @Id
    @GeneratedValue
    @Column
    val id: Int,

    @ManyToOne
    @JoinColumns(
        JoinColumn(name = "departure_stop_id", referencedColumnName = "departure_stop_id"),
        JoinColumn(name = "line_number", referencedColumnName = "line_number")
    )
    val stopConnection: StopConnectionEntity,

    @Column
    val departureTime: LocalTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is TimeDepartureEntity) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}