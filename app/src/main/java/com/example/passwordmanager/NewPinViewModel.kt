package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import com.example.passwordmanager.data.PinDao
import com.example.passwordmanager.data.PinEntity
import androidx.lifecycle.viewModelScope

class NewPinViewModel(private val dao: PinDao) : ViewModel() {
     fun newPin(Pin: String){
        val NewPin = PinEntity(pin = Pin)
        dao.insertPin(NewPin)
    }
}
