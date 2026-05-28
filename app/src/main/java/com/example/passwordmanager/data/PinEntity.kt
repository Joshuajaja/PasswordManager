package com.example.passwordmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PinEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "Pin") val pin: ByteArray // just let it be a stringg pleease,
    // now its a Bytearray nyehehehe >:3
) {
}