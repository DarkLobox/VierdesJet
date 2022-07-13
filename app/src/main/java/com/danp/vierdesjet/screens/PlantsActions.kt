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
import androidx.compose.ui.unit.dp
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.plant.PlantApp
import com.danp.vierdesjet.room.plant.PlantEntity
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream
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

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                val myInput: InputStream

                myInput = context.assets.open("plantsdata.xlsx")

                val myWorkBook = XSSFWorkbook(myInput)

                val mySheet = myWorkBook.getSheetAt(0)
                val rowIter: Iterator<Row> = mySheet.rowIterator()
                var rowno = 0

                while (rowIter.hasNext()) {
                    Log.e("TAG", " row no $rowno")
                    val myRow = rowIter.next() as XSSFRow
                    if (rowno != -1) {
                        val cellIter: Iterator<Cell> = myRow.cellIterator()
                        var colno = 0
                        var textType = ""
                        var textDescription = ""
                        var textPrice = ""
                        var textPassword = ""
                        while (cellIter.hasNext()) {
                            val myCell = cellIter.next()
                            if (colno == 0) {
                                textType = myCell.toString()
                            } else if (colno == 1) {
                                textDescription = myCell.toString()
                            } else if (colno == 2) {
                                textPrice = myCell.toString()
                            }

                            colno++
                            //Log.e("TAG", " Index :" + myCell.columnIndex + " -- " + myCell.toString())
                        }
                        val dateTemp = SimpleDateFormat("dd/M/yyyy").format(Date())
                        val plant = PlantEntity(
                            textType,
                            textPrice,
                            textDescription,
                            false,
                            false,
                            false,
                            userCode,
                            dateTemp,
                            true
                        )
                        coroutineScope.launch {
                            try {
                                plantApp.room.plantDao().insert(plant)
                            } catch (e: Exception) {
                                Log.d("Prueba", "Campos invalidos en INSERT")
                            }
                            Log.d("Prueba",
                                 plant.type + "," + plant.price + "," + plant.description + "," + plant.code)
                        }

                    }
                    rowno++
                }
            },modifier = Modifier.width(200.dp)) {
                Text("LOAD DATA")
            }
        }
    }

}