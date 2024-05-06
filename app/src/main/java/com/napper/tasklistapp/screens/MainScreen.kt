package com.napper.tasklistapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.napper.tasklistapp.R
import com.napper.tasklistapp.data.Task
import com.napper.tasklistapp.data.TaskViewModel
import com.napper.tasklistapp.data.getFakeTasks
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: TaskViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Task List")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.CREATE_TASK_SCREEN)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            BodyContent(viewModel)
        }
    }
}

@Composable
fun BodyContent(viewModel: TaskViewModel) {
    val taskList by viewModel.taskList.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        taskList?.let {
            LazyColumn {
                itemsIndexed(it) { index, item ->
                    ListItem(item, onDelete = {
                        viewModel.deleteTask(item.id)
                    })
                }
            }
        } ?: Text(
            text = "No items yet!!",
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ListItem(item: Task, onDelete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = SimpleDateFormat(
                        "hh:mm a, dd/MM",
                        Locale.ENGLISH
                    ).format(item.createdAt),
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = item.title,
                    fontSize = 33.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete icon",
                    tint = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.state.toString(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontStyle = FontStyle.Italic
            )
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    val navController = rememberNavController()
//    var dummy: Any
//    MainScreen(navController, dummy)
//}