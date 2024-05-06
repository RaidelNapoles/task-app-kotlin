package com.napper.tasklistapp.screens

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.napper.tasklistapp.R
import com.napper.tasklistapp.data.State
import com.napper.tasklistapp.data.Task
import com.napper.tasklistapp.data.TaskViewModel
import com.napper.tasklistapp.data.getFakeTasks
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Create Task")
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MainContent(navController, viewModel)
        }
    }
}

@Composable
fun MainContent(navController: NavController, viewModel: TaskViewModel) {
    var inputTitle by rememberSaveable {
        mutableStateOf("")
    }
    var inputDescription by rememberSaveable {
        mutableStateOf("")
    }
    var selectedState by rememberSaveable {
        mutableStateOf(State.PENDING)
    }

    val checkBoxLabelFontSize = 18.sp
    val mainLabelFontSize = 24.sp
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Title:",
                fontSize = mainLabelFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = inputTitle,
                onValueChange = { inputTitle = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
            )

            Text(
                text = "Description:",
                fontSize = mainLabelFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = inputDescription,
                onValueChange = { inputDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                minLines = 5,
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.LightGray)
            )

            Text(
                text = "State:",
                fontSize = mainLabelFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedState == State.PENDING,
                    onCheckedChange = { selectedState = State.PENDING })
                Text(
                    text = "Pending",
                    fontSize = checkBoxLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )

                Checkbox(
                    checked = selectedState == State.IN_PROGRESS,
                    onCheckedChange = { selectedState = State.IN_PROGRESS })
                Text(
                    text = "In Progress",
                    fontSize = checkBoxLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )

                Checkbox(
                    checked = selectedState == State.COMPLETED,
                    onCheckedChange = { selectedState = State.COMPLETED })
                Text(
                    text = "Done",
                    fontSize = checkBoxLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )
            }

            Button(
                onClick = {
                    viewModel.addTask(inputTitle, inputDescription, selectedState)
                    Toast.makeText(context, "Task created successfully!!", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(Routes.MAIN_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "Save",
                    fontSize = mainLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultUpperPreview() {
//    val navController = rememberNavController()
//    CreateTaskScreen(navController)
//}