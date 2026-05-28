package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import com.example.passwordmanager.data.PinDao
import com.example.passwordmanager.data.PinEntity
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.hashString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPinViewModel(private val dao: PinDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
       fun newPin(Pin: String){
        val hashPin = hashString(Pin)
        val hashedPin = PinEntity(pin = hashPin)
        viewModelScope.launch {
           withContext(Dispatchers.IO) {
           dao.insertPin(hashedPin) }
            _events.emit(UiEvent.NavigateToPassword)
           }
     }
}
