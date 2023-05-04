package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.TimeDepartureEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimeDepartureRepository : JpaRepository<TimeDepartureEntity?, Int?>