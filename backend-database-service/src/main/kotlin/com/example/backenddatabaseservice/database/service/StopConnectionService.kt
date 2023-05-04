package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.StopConnectionEntity
import com.example.backenddatabaseservice.database.repository.StopConnectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service

@Service
class StopConnectionService {

    @Autowired
    lateinit var stopConnectionRepository: StopConnectionRepository

    fun findByObject(stopConnection: StopConnectionEntity) : StopConnectionEntity? {
        return stopConnectionRepository.findOne(Example.of(stopConnection)).orElse(null)
    }

    fun save(stopConnection: StopConnectionEntity) {
        val that = stopConnectionRepository.findOne(Example.of(stopConnection)).orElse(null)

        if (that != null) stopConnectionRepository.delete(that)
        stopConnectionRepository.save(stopConnection)
    }

}