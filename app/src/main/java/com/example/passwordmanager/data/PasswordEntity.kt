package com.example.passwordmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Password") val password: String,
    @ColumnInfo(name = "iv") val iv: String
)