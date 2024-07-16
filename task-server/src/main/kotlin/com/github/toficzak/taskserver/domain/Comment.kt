package com.github.toficzak.taskserver.domain

import com.github.toficzak.taskserver.common.CommentId
import java.time.Instant

class Comment(
    internal val id: CommentId,
    internal val createdAt: Instant = Instant.now(),
    internal val content: String
)