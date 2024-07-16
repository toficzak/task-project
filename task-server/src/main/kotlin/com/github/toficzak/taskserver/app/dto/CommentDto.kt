package com.github.toficzak.taskserver.app.dto

import com.github.toficzak.taskserver.common.CommentId
import com.github.toficzak.taskserver.domain.Comment

data class CommentDto(
    val id: String? = null,
    val content: String
) {
    fun toDomain(): Comment {
        val commentId = id?.run { CommentId(id) } ?: CommentId.randomId()
        return Comment(id = commentId, content = content)
    }
}