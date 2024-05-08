package com.napper.tasklistapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private var _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    fun getAllTasks() {
        _taskList.value = TaskManager.getAllTasks()
    }

    fun addTask(title: String, description: String = "", state: State = State.PENDING) {
        TaskManager.addTask(title, description, state)
        getAllTasks()
    }

    fun modifyItem(itemId: Int, state: State, newTitle: String = "", newDescription: String = "") {
        val newList = _taskList.value?.toMutableList() ?: return
        val oldItem = _taskList.value?.find { it.id == itemId }
        val index = newList.indexOfFirst { it.id == itemId }
        if (index != -1) {
            _taskList.value = newList.also {
                it[index] = Task(oldItem!!.id,
                    newTitle.ifEmpty { oldItem.title },
                    newDescription.ifEmpty { oldItem.description },
                    state,
                    oldItem.createdAt
                )
            }
        }
    }

    fun deleteTask(id: Int) {
        val theList = _taskList.value?.toMutableList() ?: return
        val index = theList.indexOfFirst { it.id == id }
        if (index != -1) {
            theList.removeAt(index)
            _taskList.value = theList
        }
        TaskManager.deleteTask(id)
    }
}