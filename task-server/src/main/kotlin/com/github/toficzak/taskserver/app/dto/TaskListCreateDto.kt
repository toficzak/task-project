package com.github.toficzak.taskserver.app.dto

import com.github.toficzak.taskserver.common.TaskListId
import com.github.toficzak.taskserver.domain.TaskList
import java.time.Instant

data class TaskListCreateDto(
    val name: String
) {
    fun toDomain() = TaskList(
        id = TaskListId.random(),
        createdAt = Instant.now(),
        name = this.name
    )
}