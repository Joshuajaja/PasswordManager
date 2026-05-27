package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.data.PinDao
import kotlinx.coroutines.launch

class PasswordListViewModel (private val dao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events

    fun toNewPasswordPage(){
        viewModelScope.launch {
            _events.emit(UiEvent.NavigateToNewPass)
        }
    }
    fun toPasswordViewScreen(id: Int){
        viewModelScope.launch {
            _events.emit(UiEvent.NavigateToPasswordView(id))
        }
    }
}