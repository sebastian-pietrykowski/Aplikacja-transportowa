package com.example.backenddatabaseservice.database.entity

import jakarta.persistence.*

@Entity @Table(name = "locality")
class LocalityEntity(
    @Id
    @Column //(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    val symbol: String,

    @Column
    val name: String,

    @OneToMany(mappedBy = "locality", cascade = [CascadeType.REMOVE])
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