package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.CommentId
import com.github.toficzak.taskserver.common.TaskId
import java.time.Instant

class Task(
    internal val id: TaskId,
    internal val createdAt: Instant = Instant.now(),
    internal val name: String,
    internal val description: String?,
    internal val comments: MutableList<Comment> = mutableListOf()
) {
    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun updateComment(updatedComment: Comment) {
       comments.find { it.id == updatedComment.id}?.let {
           comments.removeIf { it.id == updatedComment.id }
           comments.add(updatedComment)
       }
    }

    fun removeComment(commentId: CommentId) {
        comments.find { it.id.id == commentId.id }?.let {
            comments.remove(it)
        }
    }
}