package com.example.passwordmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PinEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "Pin") val pin: String? // just let it be a stringg pleease
)