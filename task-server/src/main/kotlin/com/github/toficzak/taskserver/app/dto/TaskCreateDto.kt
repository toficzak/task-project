package com.github.toficzak.taskserver.app.dto

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.domain.Task

// todo: in the future - optionally in which list
data class TaskCreateDto(val name: String, val description: String? = null) {
        fun toDomain() = Task(
                id = TaskId.random(),
                name = this.name,
                description = this.description
        )
}