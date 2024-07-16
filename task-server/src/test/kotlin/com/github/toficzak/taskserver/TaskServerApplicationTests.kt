package com.github.toficzak.taskserver

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest
@Testcontainers
class TaskServerApplicationTests {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val mongoDbContainer = MongoDBContainer(DockerImageName.parse("mongo:latest"))
    }

    @Test
    fun contextLoads() {

    }

}
