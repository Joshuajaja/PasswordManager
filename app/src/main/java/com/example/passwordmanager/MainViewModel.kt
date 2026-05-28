package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.data.PinDao
import com.example.passwordmanager.data.hashString
import kotlinx.coroutines.launch

class MainViewModel(private val dao: PinDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
    var pin: String = ""

    fun pinInput(userInputPin: String) {
        pin = userInputPin
        viewModelScope.launch {
            dbCompare(pin) }
    }
     suspend fun dbCompare(pin: String){
         val hashPin = hashString(pin)
         val pinIsGood: Boolean = dao.pinCompare(hashPin)
        if (pinIsGood){
            _events.emit(UiEvent.NavigateToPasswordList)
        }
    }
}