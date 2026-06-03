package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import kotlinx.coroutines.launch

class PasswordGenViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
    var name: String = ""
    fun randomPass(inputLength: Int?) {
        val pass = StringBuilder()
        val length: Int = inputLength ?: 22
        val characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*-_?"
        for (i in 0 until length){
            pass.append(characters.random())
        }
        navigateToNewPass(name, pass.toString())
    }
    fun getName(inputName: String){
         name = inputName
    }
    fun navigateToNewPass(inputName: String, genPass: String){
        viewModelScope.launch {
            _events.emit(UiEvent.NavigateToNewPass(inputName, genPass))
        }
    }
}
