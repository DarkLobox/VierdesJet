package com.danp.vierdesjet.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun Login(
    navPlants: () -> Unit
) {
    val db = Firebase.firestore
    val coroutineScope = rememberCoroutineScope()

    //Prueba de firestore
    db.collection("prueba").get().addOnSuccessListener {
            documents -> for (document in documents){
                coroutineScope.launch {
                    Log.d("Prueba", document.getString("nombre").toString());
                }
            }
    }

    Column() {
        Text(text = "Login")

        Button(onClick = { navPlants()},  modifier = Modifier.width(200.dp)) {
            Text("SIGN UP")
        }
    }
}