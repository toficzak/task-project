package com.github.toficzak.taskserver.common

import java.util.*

class TaskListId(val id: String) {

    companion object {
        fun random() = TaskListId(UUID.randomUUID().toString())
    }

    override fun toString(): String {
        return "TaskListId(id=$id)"
    }
}