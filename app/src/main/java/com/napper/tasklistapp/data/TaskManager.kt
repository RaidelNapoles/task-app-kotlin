package com.napper.tasklistapp.data

import java.time.Instant
import java.time.ZoneId

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
                Instant.now().atZone(ZoneId.of("America/Havana"))
            )
        )
    }

    fun deleteTask(id: Int) {
        taskList.removeIf {
            it.id == id
        }
    }
}