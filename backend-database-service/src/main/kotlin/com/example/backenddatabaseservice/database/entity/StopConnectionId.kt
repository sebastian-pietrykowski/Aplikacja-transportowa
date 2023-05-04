package com.example.backenddatabaseservice.database.entity

import jakarta.persistence.*
import java.util.Objects

@Embeddable @Table(name = "stop_connection_id")
class StopConnectionId(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_stop_id")
    val departureStop: StopEntity? = null,

    @Column(name = "line_number")
    var lineNumber: String? = null,
) : java.io.Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is StopConnectionId) return false
        return other.departureStop == departureStop && other.lineNumber == lineNumber
    }

    override fun hashCode(): Int {
        return Objects.hash(lineNumber, departureStop)
    }
}