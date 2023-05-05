package com.example.backenddatabaseservice.database.entity

import jakarta.persistence.*

@Entity @Table(name = "locality")
class LocalityEntity(
    @Id
    @Column
    val symbol: String,

    @Column
    val name: String,

    @OneToMany(mappedBy = "locality", fetch = FetchType.LAZY)
    val stopComplexes: Set<StopComplexEntity>? = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        if (other !is LocalityEntity) return false
        return other.symbol == symbol
    }

    override fun hashCode(): Int {
        return symbol.hashCode()
    }
}