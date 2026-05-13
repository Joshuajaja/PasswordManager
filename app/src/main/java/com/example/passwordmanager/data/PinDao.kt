package com.example.passwordmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PinDao {
    @Query("SELECT * FROM pinentity")
    fun getAll(): List<PinEntity>

    @Query("SELECT * FROM pinentity WHERE uid IN (:pinIds)")
    fun loadAllByIds(pinIds: IntArray): List<PinEntity>
    @Query("SELECT EXISTS(SELECT 1 FROM pinentity WHERE Pin = :inputPin)")
    suspend fun pinCompare(inputPin: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM pinentity)")
    suspend fun hasAnyPin(): Boolean

    @Insert
    fun insertAll(vararg pins: PinEntity)

    @Delete
    fun delete(pin: PinEntity)
}