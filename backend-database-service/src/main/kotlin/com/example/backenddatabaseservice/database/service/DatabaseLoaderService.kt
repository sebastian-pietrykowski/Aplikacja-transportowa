package com.example.backenddatabaseservice.database.service

import com.example.backenddatabaseservice.database.entity.LocalityEntity
import com.example.backenddatabaseservice.database.entity.StopComplexEntity
import com.example.backenddatabaseservice.database.entity.StopEntity
import com.example.backenddatabaseservice.database.repository.LocalityRepository
import com.example.backenddatabaseservice.database.repository.StopComplexRepository
import com.example.backenddatabaseservice.database.repository.StopRepository
import com.example.backenddatabaseservice.database.repository.TimeStopConnectionRepository
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.nio.charset.Charset

@Service
class DatabaseLoaderService(
    private val localityRepository: LocalityRepository,
    private val stopComplexRepository: StopComplexRepository,
    private val stopConnectionRepository: TimeStopConnectionRepository,
    private val stopRepository: StopRepository,
    private val timeStopConnectionRepository: TimeStopConnectionRepository,
    private val databaseCleanerService: DatabaseCleanerService
) {
    private val filePath = "src/main/resources/RA230225-2.txt"

    fun loadDatabase() {
        databaseCleanerService.deleteAll()
        load()
    }

    private fun createReader(): BufferedReader {
        val charset = Charset.forName("UTF-8")
        return File(filePath).inputStream().bufferedReader(charset)
    }

    private fun load() {
        var section = ""
        var reader = createReader()
        var line = reader.readLine()
        while (line != null) {
            if (line.startsWith("#"))
                section = ""
            when {
                line.startsWith("*SM") -> {
                    section = "SM"
                    line = reader.readLine()
                    localityRepository.save(LocalityEntity("--", "Warszawa", null))
                    continue
                }
            }
            when (section) {
                "SM" -> saveLocality(line)
            }
            line = reader.readLine()
        }
        reader.close()
        reader = createReader()

        line = reader.readLine()
        while (line != null) {
            if (line.startsWith("#ZA") or line.startsWith("#ZP"))
                section = ""
            when {
                line.startsWith("*ZA") -> {
                    section = "ZA"
                    line = reader.readLine()
                    continue
                }
                line.startsWith("*ZP") -> {
                    section = "ZP"
                    if (!saveStop(reader)) {
                        section = ""
                        line = reader.readLine()
                    }
                    continue
                }
            }
            when (section) {
                "ZA" -> saveStopComplex(line)
//                "ZP" -> saveStop(line)
            }
            line = reader.readLine()
        }
        reader.close()
    }

    // doesnt save keys with polish characters - but we focus only on Warsaw
    private fun saveLocality(line: String) {
        val splitLine = line.trim().split("\\s{2,}".toRegex())
        val localitySymbol = splitLine[0]
        val localityName = splitLine[1]
        val locality = LocalityEntity(localitySymbol, localityName, null)
        localityRepository.save(locality)
    }

    private fun saveStopComplex(line: String) {
        val splitLine = line.trim().split("\\s{2,}".toRegex())
        val complexId = splitLine[0]
        val complexName = splitLine[1].dropLast(1)
        val complexLocalityId = splitLine[2]
        val complexLocality = localityRepository.findById(complexLocalityId).get()
        val stopComplex = StopComplexEntity(complexId, complexName, complexLocality, null)
        stopComplexRepository.save(stopComplex)
    }

    private fun saveStop(reader: BufferedReader): Boolean {
        // 1st line
        var line = reader.readLine()
        if (line.endsWith("#ZP"))
            return false
        var splitLine = line.trim().split("\\s{2,}".toRegex())
        val stopComplexId = splitLine[0]
        //val stopName = splitLine[1].dropLast(1)
        //val localitySymbol = splitLine[2]

        // 2nd line
        line = reader.readLine() // *PR
        val nOfStopsInComplex = line.trim().split("\\s+".toRegex())[1].toInt()
        for (nOfStop in 1..nOfStopsInComplex) {
            // 1st line of stop
            line = reader.readLine()
            splitLine = line.trim().split("\\s{2,}".toRegex())
            val stopId = splitLine[0]
            val nOfLinesFromStop = splitLine[1].toInt()
            val direction = splitLine[3].drop(7)
            val yCoordinate = splitLine[4].drop(3).toDoubleOrNull()
            val xCoordinate = splitLine[5].drop(3).toDoubleOrNull()
            val stopComplex = stopComplexRepository.findById(stopComplexId).get()
            val stop = StopEntity(stopId, stopComplex, xCoordinate, yCoordinate, null, null)
            stopRepository.save(stop)
            for (nOfLine in 1..nOfLinesFromStop)
                reader.readLine()
        }
        reader.readLine() // #PR
        return true
    }
}