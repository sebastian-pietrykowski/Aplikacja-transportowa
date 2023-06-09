package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.StopEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StopRepository : JpaRepository<StopEntity?, String?> {

//    @Query("SELECT s from StopEntity s where s.stopComplex.id = :complexId")
//    fun findStopsByComplexId(complexId: String): List<StopEntity>
}