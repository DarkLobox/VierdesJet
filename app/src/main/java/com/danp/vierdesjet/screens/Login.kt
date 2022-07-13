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
import kotlinx.coroutines.launch

@Composable
fun Login(
    navPlants: () -> Unit,
    navSignUp: () -> Unit,
    navHome: () -> Unit
) {
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    val userApp = UserApp(context)
    var textEmail by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Login") },
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
                value = textEmail,
                onValueChange = {textEmail = it},
                label = { Text("Email")}
            )
            Spacer(Modifier.size(10.dp, 40.dp))
            OutlinedTextField(
                value = textPassword,
                onValueChange = {textPassword = it},
                label = { Text("Password")},
            )

            Spacer(Modifier.size(10.dp, 40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        val user = userApp.room.userDao().login(textEmail, textPassword)
                        Log.d("Prueba", "Login exitoso")
                        dataStore.setUser(
                            user.email,
                            user.password,
                            user.name,
                            user.code,
                            user.dateCreation,
                            user.status.toString(),
                            user.id.toString())
                        navPlants()
                    }catch (e: Exception){
                        Log.d("Prueba", "No existe el usuario")
                    }
                }
            },  modifier = Modifier.width(200.dp)) {
                Text("LOGIN")
            }

            Spacer(Modifier.size(10.dp, 30.dp))

            Button(onClick = { navSignUp()},  modifier = Modifier.width(200.dp)) {
                Text("SIGN UP")
            }
        }
    }

}