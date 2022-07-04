package com.danp.vierdesjet.room.group

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GroupEntity::class],
    version = 1
)
abstract class GroupDb: RoomDatabase() {
    abstract fun groupDao(): GroupDao
}