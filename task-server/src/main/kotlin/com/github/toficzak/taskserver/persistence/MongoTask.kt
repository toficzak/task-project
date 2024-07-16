package com.github.toficzak.taskserver.persistence

import com.github.toficzak.taskserver.common.CommentId
import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.domain.Comment
import com.github.toficzak.taskserver.domain.Task
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("tasks")
data class MongoTask(
    @Id
    val id: String,
    val createdAt: Instant = Instant.now(),
    val name: String,
    val description: String?,
    val comments: List<MongoComment> = mutableListOf()
) {
    fun toDomain(): Task {
        return Task(
            id = TaskId(id),
            createdAt = createdAt,
            name = name,
            description = description,
            comments = comments.map {
                Comment(
                    id = CommentId(it.id),
                    content = it.content,
                    createdAt = it.createdAt
                )
            }.toMutableList()
        )
    }

    companion object {
        fun fromDomain(task: Task) = MongoTask(
            id = task.id.id,
            createdAt = task.createdAt,
            name = task.name,
            description = task.description,
            comments = task.comments.map { MongoComment(it.id.id, it.createdAt, it.content) }
        )
    }

    data class MongoComment(
        val id: String,
        val createdAt: Instant = Instant.now(),
        val content: String
    )
}