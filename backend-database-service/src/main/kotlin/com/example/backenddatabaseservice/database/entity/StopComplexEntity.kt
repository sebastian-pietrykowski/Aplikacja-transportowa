package com.example.backenddatabaseservice.database.entity

import jakarta.persistence.*

@Entity @Table(name = "stop_complex")
class StopComplexEntity (
    @Id
    @Column
    val id: String,

    @Column
    val name: String,

    @ManyToOne
    @JoinColumn(name = "locality_id")
    val locality: LocalityEntity,

    @OneToMany(mappedBy = "stopComplex", fetch = FetchType.EAGER)
    val stops: Set<StopEntity>? = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is StopComplexEntity) return false
        return other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}