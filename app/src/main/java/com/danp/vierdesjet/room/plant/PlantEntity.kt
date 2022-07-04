package com.danp.vierdesjet.room.plant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlantEntity(
    val type: String,
    val price: String,
    val description: String,
    val a: Boolean,
    val b: Boolean,
    val c: Boolean,
    val code: String,
    val dateCreation: String,
    val status: Boolean,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}