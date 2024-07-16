package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.common.TaskListId
import java.time.Instant

class TaskList(
    internal val id: TaskListId,
    internal val createdAt: Instant,
    internal val name: String,
    internal val taskIds: MutableList<TaskId> = mutableListOf()
) {

    fun append(taskId: TaskId) {
        taskIds.add(taskId)
    }

    fun remove(taskId: TaskId) {
        taskIds.removeIf { it.id == taskId.id }
    }

    fun list(): List<TaskId> = taskIds

    fun updateTaskIds(updatedTaskIds: List<TaskId>) {
        taskIds.clear()
        taskIds.addAll(updatedTaskIds)
    }

    override fun toString(): String {
        return "TaskList(id=$id, name='$name')"
    }
}