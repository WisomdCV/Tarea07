package com.example.tarea07

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ScreenUserList(navController: NavHostController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_database"
    ).build()

    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            users = db.userDao().getAllUsers()
        }
    }

    Scaffold(
        topBar = {
            Text("Lista de Usuarios", modifier = Modifier.padding(16.dp))
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Mostrar lista de usuarios
                users.forEach { user ->
                    Text("Nombre: ${user.nombre} ${user.apellido}, Correo: ${user.correo}")
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para volver a la pantalla de registro
                Button(
                    onClick = { navController.navigate("screen_user") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenUserList() {
    val navController = rememberNavController()
    ScreenUserList(navController)
}
