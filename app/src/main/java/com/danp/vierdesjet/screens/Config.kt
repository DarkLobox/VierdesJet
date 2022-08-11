package com.danp.vierdesjet.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.user.UserApp
import com.danp.vierdesjet.room.user.UserEntity
import kotlinx.coroutines.launch

@Composable
fun Config(
    navPlants: () -> Unit,
    navHome: () -> Unit
){
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    val userApp = UserApp(context)

    //Recuperacion de datos datastore
    val userEmail = dataStore.emailUser.collectAsState(initial = "").value.toString()
    val userPassword = dataStore.passwordUser.collectAsState(initial = "").value.toString()
    var userName = dataStore.nameUser.collectAsState(initial = "").value.toString()
    var userGroup = dataStore.groupUser.collectAsState(initial = "").value.toString()
    var userDateCreation = dataStore.dateCreationUser.collectAsState(initial = "").value.toString()
    var userStatus = dataStore.statusUser.collectAsState(initial = "").value.toString()
    var userId = dataStore.idUser.collectAsState(initial = "").value.toString()

    var textGroup by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "User Data", color = Color.White) },
            actions = {
                IconButton(onClick = { navPlants()}) {
                    Image(
                        painter = painterResource(R.drawable.plant_icon),
                        contentDescription = "Plants",
                        modifier = Modifier.size(24.dp)
                    )
                }
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

            Text(text = "User Data")

            Spacer(Modifier.size(40.dp, 40.dp))


            Card(elevation = 4.dp,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFF3E566)
            ) {
                Box(Modifier.padding(16.dp)) {
                    Text(
                        text =  "Name: $userName \n" +
                                "Email: $userEmail \n" +
                                "Group: $userGroup \n" +
                                "Date Creation: $userDateCreation \n" +
                                "Status: $userStatus",
                        fontSize = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.size(40.dp, 60.dp))

            Text(text = "Form Group")

            Spacer(Modifier.size(40.dp, 40.dp))

            OutlinedTextField(
                value = textGroup,
                onValueChange = {textGroup = it},
                label = { Text("Group")}
            )

            Spacer(Modifier.size(40.dp, 40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    var user = UserEntity(
                        userName,
                        userEmail,
                        userPassword,
                        textGroup,
                        userDateCreation,
                        userStatus.toBoolean(),
                        0,
                        0,
                        0,
                        0
                    )
                    try {
                        user.id = userId.toInt()
                        userApp.room.userDao().update(user)
                        dataStore.setGroup(textGroup)
                        Log.d("Prueba", "Grupo de usuario actualizado")
                        navPlants()
                    } catch (e: Exception){
                        Log.d("Prueba", "No se pudo actualizar grupo de usuario")
                    }
                } },  modifier = Modifier.width(200.dp)) {
                Text("Change Group")
            }

            Spacer(Modifier.size(40.dp, 40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    var user = UserEntity(
                        userName,
                        userEmail,
                        userPassword,
                        userGroup,
                        userDateCreation,
                        false,
                        0,
                        0,
                        0,
                        0
                    )
                    try {
                        user.id = userId.toInt()
                        userApp.room.userDao().update(user)
                        dataStore.resetUser()
                        Log.d("Prueba", "Usuario eliminado")
                        navHome()
                    } catch (e: Exception){
                        Log.d("Prueba", "No se eliminar el usuario")
                    }
                } },
                modifier = Modifier.width(200.dp),
                colors=ButtonDefaults.buttonColors(backgroundColor = Color(0xFFDF0D24))
                ) {
                Text("Delete User")
            }
        }
    }

}