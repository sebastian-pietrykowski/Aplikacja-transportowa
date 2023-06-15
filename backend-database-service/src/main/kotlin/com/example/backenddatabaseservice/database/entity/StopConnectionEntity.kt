package com.example.backenddatabaseservice.database.entity

import com.example.backenddatabaseservice.backend.model.TransportMode
import java.time.LocalTime
import jakarta.persistence.*

@Entity
@Table(
    name = "stop_connection",
    uniqueConstraints = [UniqueConstraint(columnNames = ["departure_stop_id", "line_number", "direction", "arrival_stop_id"])]
)
class StopConnectionEntity(
    @Id
    @GeneratedValue
    @Column
    val id: Long?,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_stop_id")
    val departureStop: StopEntity,

    @Column(name = "line_number")
    var lineNumber: String?,

    @Column
    val transportMode: TransportMode?,

    @Column(name = "direction")
    val direction: String,

    @ManyToOne
    @JoinColumn(name = "arrival_stop_id", nullable = true)
    val arrivalStop: StopEntity,

    @Column
    val minTransitTime: LocalTime?,

    @Column
    val maxTransitTime: LocalTime?,

    @OneToMany(mappedBy = "stopConnection", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    val timeStopConnections: Set<TimeStopConnectionEntity>? = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is StopConnectionEntity) return false
        return other.departureStop == departureStop && other.lineNumber == lineNumber
                && other.direction == direction && other.arrivalStop == arrivalStop
    }

    override fun hashCode(): Int {
        return departureStop.hashCode() + lineNumber.hashCode() + direction.hashCode() + arrivalStop.hashCode()
    }
}