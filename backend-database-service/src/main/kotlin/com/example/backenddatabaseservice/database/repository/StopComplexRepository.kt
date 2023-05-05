package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.StopComplexEntity
import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.StopEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StopComplexRepository : JpaRepository<StopComplexEntity?, String?>