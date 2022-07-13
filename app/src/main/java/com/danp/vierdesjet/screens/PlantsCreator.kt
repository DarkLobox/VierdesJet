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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlantsCreator(
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

    //Combobox elementos
    val mTypes = listOf(
        "Margarita",
        "Calabaza",
        "Texao",
        "Pino",
        "Gladiolo",
        "Tomate",
        "Asiento de Suegra",
        "Vid",
        "Salvia",
        "Lantana camara",
        "Girasol",
        "Dipladema",
        "Maiz",
        "Asclepia",
        "Retama",
        "Imperata Cylindrica",
        "Astromelia",
        "Verbena"
    )

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Plants Creator", color = Color.White) },
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
            //Combo box
            var mExpanded by remember { mutableStateOf(false) }

            var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

            // Up Icon when expanded and down icon when collapsed
            val icon = if (mExpanded)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown

            Column() {

                // Create an Outlined Text Field
                // with icon and not expanded
                OutlinedTextField(
                    value = textType,
                    onValueChange = { textType = it },
                    modifier = Modifier
                        .width(300.dp)
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            mTextFieldSize = coordinates.size.toSize()
                        },
                    label = {Text("Type")},
                    trailingIcon = {
                        Icon(icon,"contentDescription",
                            Modifier.clickable { mExpanded = !mExpanded })
                    }
                )

                // Create a drop-down menu with list of cities,
                // when clicked, set the Text Field text as the city selected
                DropdownMenu(
                    expanded = mExpanded,
                    onDismissRequest = { mExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                ) {
                    mTypes.forEach { label ->
                        DropdownMenuItem(onClick = {
                            textType = label
                            mExpanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            //Campos Editables
            OutlinedTextField(
                value = textPrice,
                onValueChange = { textPrice = it },
                label = { Text("Price") },
                modifier = Modifier.width(300.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = textDescription,
                onValueChange = { textDescription = it },
                label = { Text("Description") },
                modifier = Modifier.width(300.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
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
                        Log.d("Prueba", "Planta creada")
                        navPlants()
                    } catch (e: Exception) {
                        Log.d("Prueba", "No se pudo crear planta")
                    }
                }
            },modifier = Modifier.width(200.dp)) {
                Text("CREATE PLANT")
            }

        }
    }

}