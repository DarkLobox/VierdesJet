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
import androidx.work.*
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.R
import com.danp.vierdesjet.room.user.UserApp
import com.danp.vierdesjet.workers.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
    val userCode = dataStore.groupUser.collectAsState(initial = "").value.toString()
    var dateReset = dataStore.dateReset.collectAsState(initial = "").value.toString()

    val dateTemp = SimpleDateFormat("dd/M/yyyy").format(Date())

    if(userName.compareTo("")!=0){
        userName = "Bienvenido $userName"

        //Parametros para works
        val myData = Data.Builder()
            .putString("KEY_RESET_ARG", dateReset)
            .putString("KEY_CODE_ARG", userCode)
            .build()

        //Reset diario al entrar a la aplicacion
        val resetTaskRequest = OneTimeWorkRequestBuilder<ResetWorker>()
            .setInputData(myData)
            .build()
        WorkManager.getInstance(context).enqueue(resetTaskRequest)

        //Actualizacion de fecha luego del reseteo
        dateReset = dataStore.dateReset.collectAsState(initial = "").value.toString()

        //Notificacion por hora de riegos totales
        val plantsIrrigatedTaskRequest = PeriodicWorkRequestBuilder<PlantsIrrigatedHour>(1, TimeUnit.HOURS)
            .setInputData(myData)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "plantsIrrigatedTaskRequest",
            ExistingPeriodicWorkPolicy.KEEP,
            plantsIrrigatedTaskRequest
        )

        if(dateReset.compareTo(dateTemp)!=0){
            //Notificaciones secuenciales para riegos de dia, tarde, noche
            val notificationA = OneTimeWorkRequestBuilder<NotificationA>()
                .setInputData(myData)
                .setInitialDelay(20,TimeUnit.SECONDS)
                .build()

            val notificationB = OneTimeWorkRequestBuilder<NotificationB>()
                .setInputData(myData)
                .setInitialDelay(20,TimeUnit.SECONDS)
                .build()

            val notificationC = OneTimeWorkRequestBuilder<NotificationC>()
                .setInputData(myData)
                .setInitialDelay(20,TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context)
                .beginWith(notificationA)
                .then(notificationB)
                .then(notificationC)
                .enqueue()
        }
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