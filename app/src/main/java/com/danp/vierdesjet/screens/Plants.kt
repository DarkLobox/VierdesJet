package com.danp.vierdesjet.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.R
import com.danp.vierdesjet.paging.PlantViewModel
import com.danp.vierdesjet.room.plant.PlantEntity

@Composable
fun Plants(
    navPlantsCreator: () -> Unit,
    navPlantsDetails: (Int,String,String,String,Boolean,Boolean,Boolean,String,String,Boolean) -> Unit,
    navConfig: () -> Unit,
    navHome: () -> Unit,
    navPlantsActions: () -> Unit
) {
    //Base de datos variables
    val context = LocalContext.current
    val dataStore = DataStoreManager(context)

    //Recuperacion de datos datastore
    val userCode = dataStore.groupUser.collectAsState(initial = "").value.toString()

    val model = PlantViewModel(context,userCode)

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color(0xFF11E489),
            title = { Text(text = "Plants", color = Color.White) },
            actions = {
                IconButton(onClick = { navPlantsCreator()}) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Plant Creator")
                }
                IconButton(onClick = { navPlantsActions()}) {
                    Icon(imageVector = Icons.Filled.Build, contentDescription = "Plant Actions")
                }
                IconButton(onClick = { navConfig()}) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "User Config")
                }
                IconButton(onClick = { navHome()}) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                }
            }
        )
    }) {
        val lazyPlantItems: LazyPagingItems<PlantEntity> = model.plants.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth() ,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
        ) {
            items(lazyPlantItems.itemCount) { index ->
                lazyPlantItems[index]?.let {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors=ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF3E566)),
                        onClick = {
                            navPlantsDetails(it.id,it.type,it.price,it.description,it.a,it.b,it.c,it.code,it.dateCreation,it.status)
                        }
                    ) {
                        Column() {
                            Image(
                                painter = imagePainter(it.type),
                                contentDescription = "PlantImage",
                                modifier = Modifier.size(150.dp)
                            )
                        }

                        Column() {
                            Box(Modifier.padding(16.dp)) {
                                Text(
                                    text =  "ID: ${it.id} \n" +
                                            "Type: ${it.type} \n" +
                                            "Description: ${it.description} \n" +
                                            "1er Riego: ${it.a} \n" +
                                            "2do Riego: ${it.b} \n" +
                                            "3er Riego: ${it.c}",
                                    fontSize = 15.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

}

@Composable
fun imagePainter(type: String): Painter {
    var imagePlant = painterResource(R.drawable.margarita)

    when {
        type.compareTo("Margarita")==0 -> {
            imagePlant = painterResource(R.drawable.margarita)
        }
        type.compareTo("Calabaza")==0 -> {
            imagePlant = painterResource(R.drawable.calabaza)
        }
        type.compareTo("Texao")==0 -> {
            imagePlant = painterResource(R.drawable.texao)
        }
        type.compareTo("Pino")==0 -> {
            imagePlant = painterResource(R.drawable.pino)
        }
        type.compareTo("Gladiolo")==0 -> {
            imagePlant = painterResource(R.drawable.gladiolo)
        }
        type.compareTo("Asiento de Suegra")==0 -> {
            imagePlant = painterResource(R.drawable.asiento)
        }
        type.compareTo("Vid")==0 -> {
            imagePlant = painterResource(R.drawable.vid)
        }
        type.compareTo("Salvia")==0 -> {
            imagePlant = painterResource(R.drawable.salvia)
        }
        type.compareTo("Lantana camara")==0 -> {
            imagePlant = painterResource(R.drawable.lantana)
        }
        type.compareTo("Girasol")==0 -> {
            imagePlant = painterResource(R.drawable.girasol)
        }
        type.compareTo("Dipladema")==0 -> {
            imagePlant = painterResource(R.drawable.dipladema)
        }
        type.compareTo("Maiz")==0 -> {
            imagePlant = painterResource(R.drawable.maiz)
        }
        type.compareTo("Asclepia")==0 -> {
            imagePlant = painterResource(R.drawable.asclepia)
        }
        type.compareTo("Retama")==0 -> {
            imagePlant = painterResource(R.drawable.retama)
        }
        type.compareTo("Imperata Cylindrica")==0 -> {
            imagePlant = painterResource(R.drawable.imperata)
        }
        type.compareTo("Astromelia")==0 -> {
            imagePlant = painterResource(R.drawable.astromelia)
        }
        type.compareTo("Verbena")==0 -> {
            imagePlant = painterResource(R.drawable.verbena)
        }
    }

    return imagePlant
}