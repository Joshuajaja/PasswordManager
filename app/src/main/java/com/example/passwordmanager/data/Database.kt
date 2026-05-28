package com.example.passwordmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PinEntity::class, PasswordEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinDao(): PinDao
    abstract fun PasswordDao(): PasswordDao

}