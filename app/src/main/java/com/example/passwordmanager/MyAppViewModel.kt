package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.data.PinDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MyAppViewModel(private val dao: PinDao, private val passwordDao: PasswordDao) : ViewModel() {

        private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
        val events = _events
        val passwords: Flow<List<PasswordEntity>> = passwordDao.getAll()
        init {
            checkIfPinExist()
        }
        fun checkIfPinExist(){
            viewModelScope.launch {
                val hasPin = dao.hasAnyPin()

                if (!hasPin){
                    _events.emit(UiEvent.NavigateToNewPin)
                }
            }
        }
    }