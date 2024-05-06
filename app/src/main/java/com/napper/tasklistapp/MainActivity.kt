package com.napper.tasklistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.napper.tasklistapp.data.TaskViewModel
import com.napper.tasklistapp.screens.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        setContent {
            AppNavigation(taskViewModel)
        }
    }
}
