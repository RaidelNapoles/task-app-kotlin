package com.napper.tasklistapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

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
    var createdAt: Date
)

fun getFakeTasks(): List<Task> {
    return listOf<Task> (
        Task(1, "Finish Django Task API", "", State.COMPLETED, Date.from(Instant.now())),
        Task(2, "Finish Todo Kotlin app", "", State.IN_PROGRESS, Date.from(Instant.now())),
        Task(3, "Get a job", "", State.PENDING, Date.from(Instant.now())),
        Task(4, "Going to Cayo Coco", "", State.PENDING, Date.from(Instant.now())),
        Task(5, "Be happy", "", State.IN_PROGRESS, Date.from(Instant.now()))
    )
}