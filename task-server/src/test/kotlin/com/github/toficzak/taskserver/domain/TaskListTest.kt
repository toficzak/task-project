package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.common.TaskListId
import org.junit.jupiter.api.Test
import java.time.Instant

class TaskListTest {

    @Test
    fun appendToEmptyList() {

        val list = TaskList(
            id = TaskListId.random(),
            createdAt = Instant.now(),
            name = "test_name",
            taskIds = mutableListOf()
        )

        val newTaskId = TaskId.random()

        // when:
        list.append(newTaskId)

        // then
        val resultList = list.list()
        assert(resultList.size == 1)
        assert(resultList[0] == newTaskId)
    }

    @Test
    fun shouldAppendToPopulatedList() {

        val list = TaskList(
            id = TaskListId.random(),
            createdAt = Instant.now(),
            name = "test_name",
            taskIds = mutableListOf(TaskId.random())
        )

        val newTaskId = TaskId.random()

        // when:
        list.append(newTaskId)

        // then
        val resultList = list.list()
        assert(resultList.size == 2)
        assert(resultList.last() == newTaskId)
    }

    // todo: does not work for some reason
//    @Test
//    fun shouldList() {
//
//        val taskIds = mutableListOf<TaskId>()
//        for (i in 1L..10L) {
//            taskIds.add(TaskId.random())
//        }
//
//        val list = TaskList(
//            id = TaskListId.random(),
//            createdAt = Instant.now(),
//            name = "test_name",
//            taskIds = taskIds
//        )
//
//        // when:
//        list.list()
//
//        // then
//        val resultList = list.list()
//        assert(resultList.size == 10)
//        var expectedValue = 1L
//        resultList.forEach {
//            assert(it.id == expectedValue++)
//        }
//    }

    @Test
    fun shouldUpdateTaskIds() {
        val taskId1 = TaskId.random()
        val taskId2 = TaskId.random()
        val taskId3 = TaskId.random()
        val taskIds = mutableListOf(taskId1, taskId2, taskId3)

        val list = TaskList(
            id = TaskListId.random(),
            createdAt = Instant.now(),
            name = "test_name",
            taskIds = taskIds.toMutableList()
        )
        taskIds.reverse()

        // when:
        list.updateTaskIds(taskIds)

        // then:
        val resultList = list.list()
        assert(resultList[0] == taskId3)
        assert(resultList[2] == taskId1)
    }
}
