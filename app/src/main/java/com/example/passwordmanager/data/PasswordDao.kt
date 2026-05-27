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

    @Query("SELECT * FROM passwordentity WHERE uid IN (:passwordId)")
    suspend fun loadById(passwordId: Int): PasswordEntity

    @Query("UPDATE passwordentity SET Name = :name, Password = :password WHERE uid = :id")
    suspend fun updateById(id: Int, name: String, password: String)
    @Insert
    fun insertAll(vararg pins: PasswordEntity)

    @Delete
    fun delete(password: PasswordEntity)

    @Query("DELETE FROM passwordentity WHERE uid = :id")
    suspend fun deleteById(id: Int)
    @Insert
    fun insertPassword(password: PasswordEntity)
}