package com.github.toficzak.taskserver.persistence

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.domain.Task
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class MongoTaskRepository(
    private val mongoTemplate: MongoTemplate
) {
    fun save(task: Task): TaskId {
        val mongoTask = MongoTask.fromDomain(task)
        println("Saving $task")
        val persistedTask = save(mongoTask)
        return TaskId(persistedTask.id)
    }

    private fun save(
        mongoTask: MongoTask
    ): MongoTask {
        try {
            return mongoTemplate.save(mongoTask)
        } catch (e: Exception) {
            System.err.println("Error while saving: $mongoTask. Exception: $e")
            throw RuntimeException("Cannot save") // todo: sexier exception
        }
    }

    fun findById(taskId: TaskId): Task? {
        return mongoTemplate.findById(taskId.id, MongoTask::class.java)?.toDomain()
    }
}