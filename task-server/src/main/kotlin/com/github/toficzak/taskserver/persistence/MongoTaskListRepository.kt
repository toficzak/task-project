package com.github.toficzak.taskserver.persistence

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.common.TaskListId
import com.github.toficzak.taskserver.domain.Task
import com.github.toficzak.taskserver.domain.TaskList
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class MongoTaskListRepository(
    private val mongoTemplate: MongoTemplate
) {
    // todo: add interface
    fun list(): List<TaskList> {
        val taskLists = mongoTemplate.findAll(MongoTaskList::class.java)
        // todo: I'm too bored to move it somewhere else T_T
        val instantComparator = Comparator<MongoTaskList> { o1, o2 -> o1.createdAt.compareTo(o2.createdAt) }
        taskLists.sortedWith(instantComparator)
        return taskLists.map { it.toDomain() }
    }

    fun existsById(taskListId: TaskListId): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("id").`is`(taskListId.id))

        return mongoTemplate.exists(query, MongoTaskList::class.java)
    }

    fun findById(taskListId: TaskListId) = mongoTemplate.findById(taskListId.id, MongoTaskList::class.java)?.toDomain()

    fun findByTaskId(taskId: TaskId): TaskList? {
        val query = Query()
        query.addCriteria(Criteria.where("taskIds").`in`(setOf(taskId.id)))

        val lists = mongoTemplate.find(query, MongoTaskList::class.java)
        if (lists.size > 1) {
            throw RuntimeException("")
        }
        if (lists.size == 1) {
            return lists.first().toDomain()
        }
        return null
    }

    fun save(taskList: TaskList) {
        val mongoTaskList = MongoTaskList.fromDomain(taskList)
        save(mongoTaskList)
    }

    private fun save(
        mongoTaskList: MongoTaskList
    ) {
        try {
            mongoTemplate.save(mongoTaskList)
        } catch (e: Exception) {
            System.err.println("Error while saving: $mongoTaskList. Exception: $e")
        }
    }

    fun listTasks(taskListId: TaskListId): List<Task> {
        val taskLists = findById(taskListId)

        return taskLists?.let {
            it.list() // todo: fix it later, it's so bad xD
                .map { mongoTemplate.findById(it.id, MongoTask::class.java) }
                .map { it!!.toDomain() }
        } ?: emptyList()
    }
}