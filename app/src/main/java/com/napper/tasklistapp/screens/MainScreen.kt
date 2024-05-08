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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
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
import com.napper.tasklistapp.data.State
import com.napper.tasklistapp.data.Task
import com.napper.tasklistapp.data.TaskViewModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: TaskViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Lista de tareas", fontWeight = FontWeight.Bold)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.CREATE_TASK_SCREEN)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
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
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (taskList.isNullOrEmpty()) {
            Text(
                text = "No hay tareas",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        } else {
            taskList?.let {
                LazyColumn {
                    itemsIndexed(it) { index, item ->
                        ListItem(item, onDelete = {
                            viewModel.deleteTask(item.id)
                        },
                            onStateTextClick = {
                                val currentState = when (item.state) {
                                    State.PENDING -> State.IN_PROGRESS
                                    State.IN_PROGRESS -> State.COMPLETED
                                    State.COMPLETED -> State.COMPLETED
                                }
                                viewModel.modifyItem(item.id, currentState)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(item: Task, onDelete: () -> Unit, onStateTextClick: () -> Unit) {
    val backgroundColor = when (item.state) {
        State.PENDING -> Color(0xFF2196F3)
        State.IN_PROGRESS -> Color(0xFFFFC107)
        State.COMPLETED -> Color(0xFF118315)
    }

    val state = when (item.state) {
        State.PENDING -> "Pendiente"
        State.IN_PROGRESS -> "En curso"
        State.COMPLETED -> "Finalizada"
    }

    val pattern = "hh:mm a,  dd/MM"
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.createdAt.format(dateFormatter),
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
            ClickableText(
                text = AnnotatedString(
                    state, spanStyle = SpanStyle(
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                ),
                onClick = { onStateTextClick() })
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