package com.example.backenddatabaseservice

import com.example.backenddatabaseservice.database.entity.LocalityEntity
import com.example.backenddatabaseservice.database.repository.LocalityRepository
import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BackendDatabaseServiceApplicationTests {

    @Autowired
    lateinit var repository: LocalityRepository

    @Test
    fun contextLoads() {
        assert(true)
    }

    @Test
    fun testRepository() {
        val locality1 = LocalityEntity("WA", "Warszawa", null)
        repository.save(locality1)
        print(repository.findById("WA").get().symbol)
        assert( repository.findById("WA").get() != null)
    }

}
