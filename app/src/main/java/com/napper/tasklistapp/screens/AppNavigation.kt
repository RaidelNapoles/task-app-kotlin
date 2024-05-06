package com.napper.tasklistapp.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.napper.tasklistapp.data.TaskViewModel

@Composable
fun AppNavigation(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController, viewModel)
        }
        composable(Routes.CREATE_TASK_SCREEN) {
            CreateTaskScreen(navController, viewModel)
        }
    }
}