package com.danp.vierdesjet.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.user.UserApp
import kotlinx.coroutines.launch

@Composable
fun Home(
    navLogin: () -> Unit,
    navPlants: () -> Unit,
    navSignUp: () -> Unit
) {
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    val userApp = UserApp(context)

    //Recuperacion de datos datastore
    val userEmail = dataStore.emailUser.collectAsState(initial = "").value.toString()
    val userPassword = dataStore.passwordUser.collectAsState(initial = "").value.toString()
    var userName = dataStore.nameUser.collectAsState(initial = "").value.toString()
    if(userName.compareTo("")!=0){
        userName = "Bienvenido $userName"
    }

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Home", color = Color.White) },
            actions = {
                if(userName.compareTo("")!=0){
                    IconButton(onClick = {
                        coroutineScope.launch {
                            dataStore.resetUser()
                            Log.d("Prueba", "Logout exitoso")
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            }
        )
    }) {
        Image(
            painter = painterResource(R.drawable.vivero),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.size(10.dp, 20.dp))

            Text(text = userName)

            Spacer(Modifier.size(10.dp, 40.dp))

            Button(onClick = {
                if(userEmail.compareTo("")!=0 && userPassword.compareTo("")!=0){
                    coroutineScope.launch {
                        try {
                            val user = userApp.room.userDao().login(userEmail, userPassword)
                            Log.d("Prueba", "Logeo automatico")
                            navPlants()
                        } catch (e: Exception){
                            Log.d("Prueba", "No se pudo logear")
                        }
                    }
                }
                else{
                    Log.d("Prueba", "No hay login previo")
                    navLogin()
                }
            },  modifier = Modifier.width(150.dp)) {
                Text("Login")
            }

            Spacer(Modifier.size(10.dp, 30.dp))

            Button(onClick = { navSignUp()},  modifier = Modifier.width(150.dp)) {
                Text("Sign Up")
            }

        }
    }

}