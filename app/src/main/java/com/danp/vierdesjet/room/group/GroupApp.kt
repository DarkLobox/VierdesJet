package com.danp.vierdesjet.room.group

import android.content.Context
import androidx.room.Room

class GroupApp(context: Context) {
    val room = Room
        .databaseBuilder(context, GroupDb::class.java, "group")
        .build()
}