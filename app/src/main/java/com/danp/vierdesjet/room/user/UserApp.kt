package com.danp.vierdesjet.room.user

import android.content.Context
import androidx.room.Room

class UserApp(context: Context) {
    val room = Room
        .databaseBuilder(context, UserDb::class.java, "user")
        .build()
}