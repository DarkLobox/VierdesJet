package com.danp.vierdesjet.room.plant

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlantEntity::class],
    version = 1
)
abstract class PlantDb: RoomDatabase() {
    abstract fun plantDao(): PlantDao
}