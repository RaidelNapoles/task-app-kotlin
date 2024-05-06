package com.napper.tasklistapp.data

import android.app.ActivityManager.TaskDescription
import java.time.Instant
import java.util.Date

object TaskManager {
    private val taskList = mutableListOf<Task>()

    fun getAllTasks(): List<Task> {
        return taskList
    }

    fun addTask(title: String, description: String, state: State) {
        taskList.add(
            Task(
                System.currentTimeMillis().toInt(),
                title,
                description,
                state,
                Date.from(Instant.now())
            )
        )
    }

    fun deleteTask(id: Int) {
        taskList.removeIf {
            it.id == id
        }
    }
}