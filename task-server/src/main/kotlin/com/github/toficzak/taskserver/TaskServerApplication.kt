package com.github.toficzak.taskserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskServerApplication

fun main(args: Array<String>) {
	runApplication<TaskServerApplication>(*args)
}
