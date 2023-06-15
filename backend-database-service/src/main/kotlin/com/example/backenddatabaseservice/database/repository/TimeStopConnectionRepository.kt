package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.TimeStopConnectionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimeStopConnectionRepository : JpaRepository<TimeStopConnectionEntity?, Long?> {
//    @Query("SELECT t from TimeStopConnectionEntity t where t.stopConnection.id = :stopConnectionId")
//    fun findByStopConnectionId(stopConnectionId: Long): List<TimeStopConnectionEntity>

}