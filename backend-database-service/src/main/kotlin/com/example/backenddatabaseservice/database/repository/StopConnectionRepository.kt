package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StopConnectionRepository : JpaRepository<StopConnectionEntity?, Long?> {
    @Query("SELECT c FROM StopConnectionEntity c WHERE c.departureStop = :#{#entity.departureStop} " +
            "and c.lineNumber = :#{#entity.lineNumber} and c.direction = :#{#entity.direction} " +
            "and c.arrivalStop = :#{#entity.arrivalStop}")
    fun findFirstByDepartureStopAndLineNumberAndDirectionAndArrivalStop(
        @Param("entity") entity: StopConnectionEntity
    ): StopConnectionEntity?

//    fun findByDepartureStop(departureStopId: String) : List<StopConnectionEntity>

    @Query("SELECT c from StopConnectionEntity c where c.departureStop.id = :departureStopId")
    fun findByDepartureStopId(departureStopId: String): List<StopConnectionEntity>


}