package com.example.backenddatabaseservice.database.repository

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.entity.StopConnectionId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StopConnectionRepository : JpaRepository<StopConnectionEntity?, StopConnectionId?>