package com.danp.vierdesjet.room.user

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getById(id:Int): UserEntity

    @Query("SELECT * FROM UserEntity WHERE email = :email AND password = :password AND status =:status")
    suspend fun login(email:String, password:String, status:Boolean = true): UserEntity

    @Update
    suspend fun update(userEntity: UserEntity)

    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

}