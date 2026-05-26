package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPassViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events

    suspend fun newPass(name: String, password: String){
        val newPassword = PasswordEntity(name = name, password = password)
        if(name == "" || password == ""){_events.emit(UiEvent.NavigateToNewPass)}
        else{
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                passDao.insertPassword(newPassword) }
            _events.emit(UiEvent.NavigateToPasswordList)
        }
    }
    }
}
