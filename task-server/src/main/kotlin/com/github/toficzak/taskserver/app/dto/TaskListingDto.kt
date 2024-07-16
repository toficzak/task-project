package com.github.toficzak.taskserver.app.dto

import com.github.toficzak.taskserver.domain.Task

data class TaskListingDto(
    val id: String,
    val name: String
) {
    companion object {
        fun fromDomain(task: Task): TaskListingDto {
            return TaskListingDto(
                id = task.id.id,
                name = task.name
            )
        }
    }
}