package com.github.toficzak.taskserver.app.dto

import com.github.toficzak.taskserver.domain.Task

data class TaskDetailsDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val comments : List<CommentDetailsDto>
) {
    companion object{
        fun from(task : Task): TaskDetailsDto {
            return TaskDetailsDto(
                task.id.id,
                task.name,
                task.description,
                task.comments.map {
                    CommentDetailsDto(it.id.id, it.content)
                }
            )
        }
    }
}