package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.CommentId
import com.github.toficzak.taskserver.common.TaskId
import com.github.toficzak.taskserver.domain.Comment
import com.github.toficzak.taskserver.domain.Task
import org.junit.jupiter.api.Test

class TaskTest {

    @Test
    fun shouldAddComments() {
        val task = Task(
            id = TaskId.random(),
            name = "name",
            description = "description"
        )

        val comment1 = Comment(
            id = CommentId.randomId(),
            content = "content1"
        )
        val comment2 = Comment(
            id = CommentId.randomId(),
            content = "content2"
        )

        task.addComment(comment1)
        task.addComment(comment2)

        assert(task.comments.size == 2)
    }

    @Test
    fun shouldUpdateComment() {
        val task = Task(
            id = TaskId.random(),
            name = "name",
            description = "description",
            comments = mutableListOf(
                Comment(
                    id = CommentId.randomId(),
                    content = "old_value"
                )
            )
        )

        val updatedComment = Comment(
            id = task.comments.first().id,
            content = "new_value"
        )

        task.updateComment(updatedComment)

        assert(task.comments.size == 1)
        assert(task.comments.first() == updatedComment)
    }

    @Test
    fun shouldRemoveComment() {
        val task = Task(
            id = TaskId.random(),
            name = "name",
            description = "description",
            comments = mutableListOf(
                Comment(
                    id = CommentId.randomId(),
                    content = "old_value"
                )
            )
        )

        task.removeComment(task.comments.first().id)
        assert(task.comments.size == 0)
    }
}