package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PinDao
import kotlinx.coroutines.launch

class MainViewModel(private val dao: PinDao) : ViewModel() {
    var pin: String = ""

    init {
        checkIfPinExist()
    }
    fun checkIfPinExist(){
        viewModelScope.launch {
            val hasPin = dao.hasAnyPin()

            if (!hasPin){}
            else{}
        }
    }
    fun PinInput(userInputPin: String) {
        pin = userInputPin
        viewModelScope.launch {
            dbCompare(pin) }
    }
     suspend fun dbCompare(pin: String){
        val pinIsGood: Boolean = dao.pinCompare(pin)
        if (pinIsGood){
            val text: String = "wooooo"
        }
        else { val text: String = "Andere wooooooo"}
    }
}