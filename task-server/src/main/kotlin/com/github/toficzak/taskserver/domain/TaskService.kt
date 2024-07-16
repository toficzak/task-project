package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.CommentId
import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.persistence.MongoTaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: MongoTaskRepository) {

    fun create(task: Task) : TaskId {
        return taskRepository.save(task)
    }

    fun addComment(taskId: TaskId, comment: Comment) {
        taskRepository.findById(taskId)?.let {
            it.addComment(comment)
            taskRepository.save(it)
            println("Added comment to $taskId")
        }

    }

    fun updateComment(taskId: TaskId, comment: Comment) {
        taskRepository.findById(taskId)?.let {
            it.updateComment(comment)
            taskRepository.save(it)
            println("Updated comment ${comment.id} in $taskId")
        }
    }

    fun removeComment(taskId: TaskId, commentId: CommentId) {
        taskRepository.findById(taskId)?.let {
            it.removeComment(commentId)
            taskRepository.save(it)
            println("Removed comment $commentId from $taskId")
        }

    }

    fun get(taskId: TaskId) = taskRepository.findById(taskId)

}