package com.danp.vierdesjet.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val name: String,
    val email: String,
    val password: String,
    val code: String,
    val dateCreation: String,
    val status: Boolean,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}