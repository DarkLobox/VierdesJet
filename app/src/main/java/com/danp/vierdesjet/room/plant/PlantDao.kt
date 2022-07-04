package com.danp.vierdesjet.room.plant

import androidx.room.*

@Dao
interface PlantDao {
    @Query("SELECT * FROM PlantEntity")
    suspend fun getAll(): List<PlantEntity>

    @Query("SELECT * FROM PlantEntity WHERE id = :id")
    suspend fun getById(id:Int): PlantEntity

    @Query("SELECT * FROM PlantEntity WHERE id = :id AND code = :group AND status = :status")
    suspend fun getByGroup(id:Int,group:String,status:Boolean = true): PlantEntity

    @Update
    suspend fun update(plantEntity: PlantEntity)

    @Query("UPDATE PlantEntity SET a=:value WHERE id = :id")
    suspend fun updateA(id: Int, value:Boolean = true)

    @Query("UPDATE PlantEntity SET b=:value WHERE id = :id")
    suspend fun updateB(id: Int, value:Boolean = true)

    @Query("UPDATE PlantEntity SET c=:value WHERE id = :id")
    suspend fun updateC(id: Int, value:Boolean = true)

    @Query("UPDATE PlantEntity SET a=:value WHERE code = :group")
    suspend fun updateAllA(group:String,value:Boolean = true)

    @Query("UPDATE PlantEntity SET b=:value WHERE code = :group")
    suspend fun updateAllB(group:String,value:Boolean = true)

    @Query("UPDATE PlantEntity SET c=:value WHERE code = :group")
    suspend fun updateAllC(group:String,value:Boolean = true)

    @Query("UPDATE PlantEntity SET a=:value WHERE code = :group")
    suspend fun resetAllA(group:String,value:Boolean = false)

    @Query("UPDATE PlantEntity SET b=:value WHERE code = :group")
    suspend fun resetAllB(group:String,value:Boolean = false)

    @Query("UPDATE PlantEntity SET c=:value WHERE code = :group")
    suspend fun resetAllC(group:String,value:Boolean = false)

    @Query("UPDATE PlantEntity SET status=:value WHERE id = :id")
    suspend fun updateStatus(id: Int, value:Boolean = false)

    @Insert
    suspend fun insert(plantEntity: PlantEntity)

    @Delete
    suspend fun delete(plantEntity: PlantEntity)

}