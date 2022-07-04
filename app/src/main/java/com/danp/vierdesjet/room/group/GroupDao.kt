package com.danp.vierdesjet.room.group

import androidx.room.*

@Dao
interface GroupDao {
    @Query("SELECT * FROM GroupEntity")
    suspend fun getAll(): List<GroupEntity>

    @Query("SELECT * FROM GroupEntity WHERE id = :id")
    suspend fun getById(id:Int): GroupEntity

    @Update
    suspend fun update(groupEntity: GroupEntity)

    @Insert
    suspend fun insert(groupEntity: GroupEntity)

    @Delete
    suspend fun delete(groupEntity: GroupEntity)

}