package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.common.TaskListId
import com.github.toficzak.taskserver.persistence.MongoTaskListRepository
import org.springframework.stereotype.Service

@Service
class TaskListService(
    private val mongoTaskListRepository: MongoTaskListRepository
) {
    fun getLists(): List<TaskList> {
        return mongoTaskListRepository.list()
    }

    fun create(taskList: TaskList) {
        mongoTaskListRepository.save(taskList)
        println("Saved $taskList")
    }

    fun addTask(taskListId: TaskListId, taskId: TaskId) {
        mongoTaskListRepository.findById(taskListId)?.let {
            it.append(taskId)
            mongoTaskListRepository.save(it)
            println("Added $taskId to $taskListId")
        }
    }

    fun removeTask(taskId: TaskId) {
        mongoTaskListRepository.findByTaskId(taskId)?.let {
            it.remove(taskId)
            mongoTaskListRepository.save(it)
            println("Removed $taskId to ${it.id}")
        }
    }

    fun listTasks(taskListId: TaskListId): List<Task> {
        return mongoTaskListRepository.listTasks(taskListId)
    }

    fun existsOrThrowException(taskListId: TaskListId) {
        if (!mongoTaskListRepository.existsById(taskListId)) {
            throw Exception("$taskListId not found")
        }

    }
}