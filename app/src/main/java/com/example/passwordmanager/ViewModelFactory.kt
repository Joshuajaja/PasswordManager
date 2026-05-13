package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.data.PinDao
import kotlin.jvm.java

class ViewModelFactory(
    private val dao: PinDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(dao) as T

            modelClass.isAssignableFrom(PasswordListViewModel::class.java) ->
                PasswordListViewModel(dao) as T

            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}