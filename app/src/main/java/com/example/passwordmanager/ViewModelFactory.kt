package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PinDao
import kotlin.jvm.java

class ViewModelFactory(
    private val dao: PinDao,
    private val passwordDao: PasswordDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(dao) as T

            modelClass.isAssignableFrom(MyAppViewModel::class.java) ->
                MyAppViewModel(dao, passwordDao) as T

            modelClass.isAssignableFrom(NewPinViewModel::class.java) ->
                NewPinViewModel(dao) as T
            modelClass.isAssignableFrom(PasswordListViewModel::class.java) ->
                PasswordListViewModel(passwordDao) as T

            modelClass.isAssignableFrom(NewPassViewModel::class.java) ->
                NewPassViewModel(passwordDao) as T
            modelClass.isAssignableFrom(PasswordViewViewModel::class.java) ->
                PasswordViewViewModel(passwordDao) as T

            modelClass.isAssignableFrom(EditPasswordViewModel::class.java) ->
                EditPasswordViewModel(passwordDao) as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}