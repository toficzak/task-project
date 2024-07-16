package com.github.toficzak.taskserver.common

import java.util.*

class TaskId(val id: String) {
    companion object {
        fun random() = TaskId(UUID.randomUUID().toString())
    }

    override fun toString(): String {
        return "TaskId(id=$id)"
    }
}