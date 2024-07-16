package com.github.toficzak.taskserver.app

import com.github.toficzak.taskserver.app.dto.CommentDto
import com.github.toficzak.taskserver.app.dto.TaskDetailsDto
import com.github.toficzak.taskserver.common.CommentId
import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.common.TaskListId
import com.github.toficzak.taskserver.domain.TaskListService
import com.github.toficzak.taskserver.domain.TaskService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskEndpoint(
    private val taskService: TaskService,
    private val taskListService: TaskListService
) {

    @GetMapping("/{taskId}")
    fun showTask(
        @PathVariable("taskId") taskIdString: String, // todo: taskId right away?
    ): TaskDetailsDto? {
        val taskId = TaskId(taskIdString)

        return taskService.get(taskId)?.let { TaskDetailsDto.from(it) }
    }

    @PostMapping("/{taskId}/list/{taskListId}")
    fun moveTask(
        @PathVariable("taskId") taskIdString: String, // todo: taskId right away?
        @PathVariable("taskListId") taskListIdString: String // todo: TaskListId right away?
    ) {
        val taskId = TaskId(taskIdString)
        val taskListId = TaskListId(taskListIdString)

        taskListService.removeTask(taskId)
        taskListService.addTask(taskListId, taskId)
    }

    @PostMapping("/{taskId}/comments")
    fun addComment(
        @PathVariable("taskId") taskIdString: String, // todo: taskId right away?
        @RequestBody commentDto: CommentDto
    ) {
        val taskId = TaskId(taskIdString)

        taskService.addComment(taskId, commentDto.toDomain())
    }

    @PutMapping("/{taskId}/comments")
    fun updateComment(
        @PathVariable("taskId") taskIdString: String, // todo: taskId right away?
        @RequestBody commentDto: CommentDto
    ) {
        val taskId = TaskId(taskIdString)

        taskService.updateComment(taskId, commentDto.toDomain())
    }

    @DeleteMapping("{taskId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable("taskId") taskIdString: String,  // todo: taskId right away?
        @PathVariable("commentId") commentIdString: String // todo: taskId right away?
    ) {
        val taskId = TaskId(taskIdString)
        val commentId = CommentId(commentIdString)

        taskService.removeComment(taskId, commentId)
    }

}