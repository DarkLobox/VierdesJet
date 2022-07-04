package com.danp.vierdesjet.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.plant.PlantApp
import com.danp.vierdesjet.room.plant.PlantEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlantsActions(
    navPlants: () -> Unit,
    navHome: () -> Unit
) {
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    val plantApp = PlantApp(context)

    //Recuperacion de datos datastore
    val userEmail = dataStore.emailUser.collectAsState(initial = "").value.toString()
    val userPassword = dataStore.passwordUser.collectAsState(initial = "").value.toString()
    val userCode = dataStore.groupUser.collectAsState(initial = "").value.toString()

    //Variables mutables para el formulario
    var textType by remember { mutableStateOf("") }
    var textPrice by remember { mutableStateOf("") }
    var textDescription by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Plants Actions", color = Color.White) },
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
            Button(onClick = {
                coroutineScope.launch {
                    try {
                        plantApp.room.plantDao().updateAllA(userCode)
                        Log.d("Prueba", "Plantas A Checkeadas")
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear checkear A")
                    }
                }
            },modifier = Modifier.width(200.dp)) {
                Text("CHECK ALL A")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        plantApp.room.plantDao().updateAllB(userCode)
                        Log.d("Prueba", "Plantas B Checkeadas")
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear checkear B")
                    }
                }
            },modifier = Modifier.width(200.dp)) {
                Text("CHECK ALL B")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        plantApp.room.plantDao().updateAllC(userCode)
                        Log.d("Prueba", "Plantas C Checkeadas")
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear checkear C")
                    }
                }
            },modifier = Modifier.width(200.dp)) {
                Text("CHECK ALL C")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        plantApp.room.plantDao().updateAllA(userCode)
                        plantApp.room.plantDao().updateAllB(userCode)
                        plantApp.room.plantDao().updateAllC(userCode)
                        Log.d("Prueba", "Plantas Checkeadas")
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear checkear")
                    }
                }
            },modifier = Modifier.width(200.dp)) {
                Text("CHECK ALL")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        plantApp.room.plantDao().resetAllA(userCode)
                        plantApp.room.plantDao().resetAllB(userCode)
                        plantApp.room.plantDao().resetAllC(userCode)
                        Log.d("Prueba", "Valores reseteados")
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear planta")
                    }
                }
            },modifier = Modifier.width(200.dp)) {
                Text("RESET")
            }
        }
    }

}