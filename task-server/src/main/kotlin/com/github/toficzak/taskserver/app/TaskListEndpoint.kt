package com.github.toficzak.taskserver.app

import com.github.toficzak.taskserver.app.dto.TaskCreateDto
import com.github.toficzak.taskserver.app.dto.TaskListCreateDto
import com.github.toficzak.taskserver.app.dto.TaskListListingDto
import com.github.toficzak.taskserver.app.dto.TaskListingDto
import com.github.toficzak.taskserver.common.TaskListId
import com.github.toficzak.taskserver.domain.TaskListService
import com.github.toficzak.taskserver.domain.TaskService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/lists")
class TaskListEndpoint(
    private val taskListService: TaskListService,
    private val taskService: TaskService
) {

    @PostMapping
    fun create(@RequestBody createTaskListDto: TaskListCreateDto) {
        taskListService.create(createTaskListDto.toDomain())
    }

    @GetMapping("/{taskListId}/tasks")
    fun listTask(
        @PathVariable("taskListId") taskListIdString: String // todo: TaskListId right away?
    ): List<TaskListingDto> {
        val taskListId = TaskListId(taskListIdString)
        return taskListService.listTasks(taskListId).map { TaskListingDto.fromDomain(it) }
    }

    @PostMapping("/{taskListId}/tasks")
    fun createTask(
        @PathVariable("taskListId") taskListIdString: String, // todo: TaskListId right away?
        @RequestBody createTaskDto: TaskCreateDto
    ) {
        val taskListId = TaskListId(taskListIdString)
        val task = createTaskDto.toDomain()

        taskListService.existsOrThrowException(taskListId)
        val persistedTaskId = taskService.create(task)
        taskListService.addTask(taskListId, persistedTaskId)
    }

    @GetMapping
    fun getTaskLists(): List<TaskListListingDto> {
        return taskListService.getLists().map { TaskListListingDto.fromDomain(it) }
    }
}