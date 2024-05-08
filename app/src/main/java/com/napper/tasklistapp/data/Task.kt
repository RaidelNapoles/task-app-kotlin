package com.napper.tasklistapp.data

import java.time.ZonedDateTime

enum class State {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}

data class Task(
    var id: Int,
    var title: String,
    var description: String,
    var state: State,
    var createdAt: ZonedDateTime
)

