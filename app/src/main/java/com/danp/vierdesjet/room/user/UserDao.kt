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

    @Query("SELECT plantsIrrigated FROM UserEntity WHERE code = :code")
    suspend fun getPlantsIrrigated(code:String) : Int

    @Query("UPDATE UserEntity SET plantsIrrigated=:number WHERE code = :code")
    suspend fun setPlantsIrrigated(number: Int, code:String)

    @Query("SELECT plantsIrrigatedA FROM UserEntity WHERE code = :code")
    suspend fun getPlantsIrrigatedA(code:String) : Int

    @Query("UPDATE UserEntity SET plantsIrrigatedA=:number WHERE code = :code")
    suspend fun setPlantsIrrigatedA(number: Int, code:String)

    @Query("SELECT plantsIrrigatedB FROM UserEntity WHERE code = :code")
    suspend fun getPlantsIrrigatedB(code:String) : Int

    @Query("UPDATE UserEntity SET plantsIrrigatedB=:number WHERE code = :code")
    suspend fun setPlantsIrrigatedB(number: Int, code:String)

    @Query("SELECT plantsIrrigatedC FROM UserEntity WHERE code = :code")
    suspend fun getPlantsIrrigatedC(code:String) : Int

    @Query("UPDATE UserEntity SET plantsIrrigatedC=:number WHERE code = :code")
    suspend fun setPlantsIrrigatedC(number: Int, code:String)

    @Query("SELECT setNotification FROM UserEntity WHERE code = :code")
    suspend fun getNotification(code:String) : Boolean

    @Query("UPDATE UserEntity SET setNotification=:status WHERE code = :code")
    suspend fun setNotification(status:Boolean, code:String)
}