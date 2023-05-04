package com.example.backenddatabaseservice.database.entity

import com.example.backenddatabaseservice.backend.model.StopType
import jakarta.persistence.*

@Entity @Table(name = "stop")
class StopEntity(
    @Id
    @Column
    val id: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stop_complex_id")
    val stopComplex: StopComplexEntity,

    @Column
    val stopType: StopType,

    @Column
    val direction: String,

    @Column
    val xCoordinate: Double,

    @Column
    val yCoordinate: Double,

    @OneToMany(mappedBy = "departureStop")
    val departureConnections: Set<StopConnectionEntity> = mutableSetOf(),

    @OneToMany(mappedBy = "arrivalStop")
    val arrivalConnections: Set<StopConnectionEntity> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is StopEntity) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}