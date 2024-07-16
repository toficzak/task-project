package com.github.toficzak.taskserver.common

import java.util.*

data class CommentId(val id: String) {
    companion object {
        fun randomId() = CommentId(UUID.randomUUID().toString())
    }
}