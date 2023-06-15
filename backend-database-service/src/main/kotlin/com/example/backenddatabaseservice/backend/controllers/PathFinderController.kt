package com.example.backenddatabaseservice.backend.controllers

import com.example.backenddatabaseservice.backend.model.StopConnectionWithArrival
import com.example.backenddatabaseservice.backend.model.StopConnectionWithDeparture
import com.example.backenddatabaseservice.backend.service.ShortestPathAlgorithm
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalTime

@RestController
@RequestMapping("/findPath")
class PathFinderController(
    private val shortestPathAlgorithm: ShortestPathAlgorithm
) {
    @GetMapping("/find")
    fun findShortestPath(
        @RequestParam("sourceComplexId") sourceComplexId: String,
        @RequestParam("departureTime") @DateTimeFormat(pattern = "HH:mm") departureTime: LocalTime,
        @RequestParam("destinationComplexId") destinationComplexId: String
    ): List<StopConnectionWithDeparture> {
        return shortestPathAlgorithm.find(sourceComplexId, departureTime, destinationComplexId)
    }
}