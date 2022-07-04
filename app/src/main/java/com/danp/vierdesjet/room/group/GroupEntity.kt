package com.danp.vierdesjet.room.group

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupEntity(
    val code: String,
    val dateCreation: String,
    val status: Boolean
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}