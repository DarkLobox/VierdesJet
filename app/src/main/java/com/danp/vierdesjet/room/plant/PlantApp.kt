package com.danp.vierdesjet.room.plant

import android.content.Context
import androidx.room.Room

class PlantApp(context: Context) {
    val room = Room
        .databaseBuilder(context, PlantDb::class.java, "plant")
        .build()
}