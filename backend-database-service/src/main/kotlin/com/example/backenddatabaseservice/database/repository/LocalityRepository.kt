package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.LocalityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalityRepository : JpaRepository<LocalityEntity?, String?>