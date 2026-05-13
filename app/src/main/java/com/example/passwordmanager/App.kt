package com.example.passwordmanager

import android.app.Application
import androidx.room.Room
import com.example.passwordmanager.data.AppDatabase
import kotlin.getValue


class PasswordManagerApp : Application() {
    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "PasswordManager"
        ).build()
    }
}