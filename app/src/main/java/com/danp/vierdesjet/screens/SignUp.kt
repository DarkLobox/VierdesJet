package com.danp.vierdesjet.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.room.user.UserApp
import com.danp.vierdesjet.room.user.UserEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SignUp(
    navPlants: () -> Unit,
    navHome: () -> Unit
) {
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    val userApp = UserApp(context)

    var textName by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textCode by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Sign Up", color = Color.White) },
            actions = {
                IconButton(onClick = { navHome()}) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                }
            }
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = textName,
                onValueChange = {textName = it},
                label = { Text("Name")}
            )
            OutlinedTextField(
                value = textEmail,
                onValueChange = {textEmail = it},
                label = { Text("Email")},
            )
            OutlinedTextField(
                value = textPassword,
                onValueChange = {textPassword = it},
                label = { Text("Password")},
            )
            OutlinedTextField(
                value = textCode,
                onValueChange = {textCode = it},
                label = { Text("Code")},
            )

            Spacer(Modifier.size(10.dp, 40.dp))

            Button(onClick = {
                val dateTemp = SimpleDateFormat("dd/M/yyyy").format(Date())
                val user = UserEntity(
                    textName,
                    textEmail,
                    textPassword,
                    textCode,
                    dateTemp,
                    true,
                    0,
                    0,
                    0,
                    0
                )
                coroutineScope.launch {
                    try {
                        userApp.room.userDao().insert(user)
                        val user = userApp.room.userDao().login(textEmail, textPassword)
                        dataStore.setUser(
                            user.email,
                            user.password,
                            user.name,
                            user.code,
                            user.dateCreation,
                            user.status.toString(),
                            user.id.toString())
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear user")
                    }
                }
            },  modifier = Modifier.width(200.dp)) {
                Text("CREATE")
            }
        }
    }

}