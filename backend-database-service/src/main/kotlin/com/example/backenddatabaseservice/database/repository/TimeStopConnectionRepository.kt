package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TimeStopConnectionRepository : JpaRepository<TimeStopConnectionEntity?, Long?> {
//    @Query("SELECT t from TimeStopConnectionEntity t where t.stopConnection.id = :stopConnectionId")
//    fun findByStopConnectionId(stopConnectionId: Long): List<TimeStopConnectionEntity>

//    fun countById(): Long
}