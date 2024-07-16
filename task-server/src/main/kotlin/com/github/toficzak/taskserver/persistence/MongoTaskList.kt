package com.github.toficzak.taskserver.persistence

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.common.TaskListId
import com.github.toficzak.taskserver.domain.TaskList
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document("lists")
class MongoTaskList(
    @Id
    val id: String,
    val createdAt: Instant = Instant.now(),
    val name: String,
    val taskIds: MutableList<String> = mutableListOf()
) {
    fun toDomain() = TaskList(
        id = TaskListId(id),
        createdAt = createdAt,
        name = name,
        taskIds = taskIds.map { TaskId(it) }.toMutableList()
    )

    companion object {
        fun fromDomain(taskList: TaskList) = MongoTaskList(
            id = taskList.id.id,
            createdAt = taskList.createdAt,
            name = taskList.name,
            taskIds = taskList.taskIds.map { it.id }.toMutableList()
        )
    }
}