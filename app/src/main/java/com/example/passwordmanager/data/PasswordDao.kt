package com.example.passwordmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwordentity")
    fun getAll(): Flow<List<PasswordEntity>>
    @Query("SELECT * FROM passwordentity WHERE uid IN (:passwordIds)")
    fun loadAllByIds(passwordIds: IntArray): List<PasswordEntity>

    @Insert
    fun insertAll(vararg pins: PasswordEntity)

    @Delete
    fun delete(password: PasswordEntity)

    @Insert
    fun insertPassword(password: PasswordEntity)
}