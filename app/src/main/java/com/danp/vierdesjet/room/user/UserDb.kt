package com.danp.vierdesjet.room.user

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UserDb: RoomDatabase() {
    abstract fun userDao(): UserDao
}