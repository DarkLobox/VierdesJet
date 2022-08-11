package com.danp.vierdesjet.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.danp.vierdesjet.PlantStatus
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.plant.PlantApp
import kotlinx.coroutines.launch

@Composable
fun PlantsDetails(
    plantId: Int,
    plantType: String,
    plantPrice: String,
    plantDescription: String,
    plantA: Boolean,
    plantB: Boolean,
    plantC: Boolean,
    plantCode: String,
    plantDate: String,
    plantStatus: Boolean,
    navPlants: () -> Unit,
    navHome: () -> Unit
) {
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    val plantApp = PlantApp(context)

    var plantsIrrigated = dataStore.plantIrrigated.collectAsState(initial = "0").value.toString().toInt()

    val status = remember{
        mutableStateListOf<PlantStatus>()
    }
    status.clear()
    status.add(PlantStatus(plantA,plantB,plantC))

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Plants Detail", color = Color.White) },
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
            Image(
                painter = imagePainter(plantType),
                contentDescription = "Plant Type",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(Modifier.padding(16.dp)) {
                Text(
                    text =  "Type: $plantType \n" +
                            "Price: $plantPrice \n" +
                            "Description: $plantDescription \n" +
                            "Code: $plantCode \n" +
                            "Date Creation: $plantDate \n",
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn{
                itemsIndexed(status) { index, item ->
                    Button(enabled = item.a == false, onClick = {
                        coroutineScope.launch {
                            try {
                                plantsIrrigated += 1
                                dataStore.setPlantIrrigated(plantsIrrigated.toString())
                                Log.d("Prueba", plantsIrrigated.toString())

                                plantApp.room.plantDao().updateA(plantId)
                                Log.d("Prueba", "Cambio realizado en planta A")
                                status.set(index, PlantStatus(true,item.b,item.c))
                            }catch (e: Exception){
                                Log.d("Prueba", "No se realizo el cambio en A")
                            }
                        }
                    }, modifier = Modifier.width(150.dp)) {
                        Text("Morning")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(enabled = item.b == false, onClick = {
                        coroutineScope.launch {
                            try {
                                plantsIrrigated += 1
                                dataStore.setPlantIrrigated(plantsIrrigated.toString())
                                Log.d("Prueba", plantsIrrigated.toString())

                                plantApp.room.plantDao().updateB(plantId)
                                Log.d("Prueba", "Cambio realizado en planta B")
                                status.set(index, PlantStatus(item.a,true,item.c))
                            }catch (e: Exception){
                                Log.d("Prueba", "No se realizo el cambio en B")
                            }
                        }
                    }, modifier = Modifier.width(150.dp)) {
                        Text("Afternoon")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(enabled = item.c == false, onClick = {
                        coroutineScope.launch {
                            try {
                                plantsIrrigated += 1
                                dataStore.setPlantIrrigated(plantsIrrigated.toString())
                                Log.d("Prueba", plantsIrrigated.toString())
                                
                                plantApp.room.plantDao().updateC(plantId)
                                Log.d("Prueba", "Cambio realizado en planta C")
                                status.set(index, PlantStatus(item.a,item.b,true))
                            }catch (e: Exception){
                                Log.d("Prueba", "No se realizo el cambio en C")
                            }
                        }
                    }, modifier = Modifier.width(150.dp) ) {
                        Text("Evening")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        plantApp.room.plantDao().updateStatus(plantId)
                        Log.d("Prueba", "Cambio realizado en planta Status")
                        navPlants()
                    }catch (e: Exception){
                        Log.d("Prueba", "No se realizo el cambio en Status")
                    }
                }
            },
                modifier = Modifier.width(200.dp),
                colors=ButtonDefaults.buttonColors(backgroundColor = Color(0xFFDF0D24))
                ) {
                Text("Delete Plant")
            }

        }
    }

}