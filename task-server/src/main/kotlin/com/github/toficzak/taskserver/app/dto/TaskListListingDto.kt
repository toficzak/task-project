package com.github.toficzak.taskserver.app.dto

import com.github.toficzak.taskserver.domain.TaskList

data class TaskListListingDto(
    val id: String,
    val name: String
) {
    companion object {
        fun fromDomain(taskList: TaskList): TaskListListingDto {
            return TaskListListingDto(
                id = taskList.id.id,
                name = taskList.name
            )
        }
    }
}