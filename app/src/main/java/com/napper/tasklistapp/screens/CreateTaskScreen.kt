package com.napper.tasklistapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.napper.tasklistapp.data.State
import com.napper.tasklistapp.data.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("Crear tarea", fontWeight = FontWeight.Bold)
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
                text = "Título:",
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
                text = "Descripción (opcional):",
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
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, color = Color.DarkGray)
            )

            Text(
                text = "Estado:",
                fontSize = mainLabelFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = selectedState == State.PENDING,
                    onCheckedChange = { selectedState = State.PENDING })
                Text(
                    text = "Pendiente",
                    fontSize = checkBoxLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedState == State.IN_PROGRESS,
                    onCheckedChange = { selectedState = State.IN_PROGRESS })
                Text(
                    text = "En curso",
                    fontSize = checkBoxLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically,) {
                Checkbox(
                    checked = selectedState == State.COMPLETED,
                    onCheckedChange = { selectedState = State.COMPLETED })
                Text(
                    text = "Finalizada",
                    fontSize = checkBoxLabelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )
            }

            Button(
                onClick = {
                    viewModel.addTask(inputTitle, inputDescription, selectedState)
                    Toast.makeText(context, "La tarea se creó exitosamente", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(Routes.MAIN_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                enabled = inputTitle.isNotEmpty()
            ) {
                Text(
                    text = "Guardar",
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