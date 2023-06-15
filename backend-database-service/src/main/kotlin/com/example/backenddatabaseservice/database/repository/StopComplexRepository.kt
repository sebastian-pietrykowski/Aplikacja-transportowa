package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.StopComplexEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StopComplexRepository : JpaRepository<StopComplexEntity?, String?>