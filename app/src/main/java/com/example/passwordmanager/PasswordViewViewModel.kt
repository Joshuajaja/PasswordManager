package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PasswordViewViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
    val password = MutableStateFlow<PasswordEntity?>(null)

    fun loadPassword(id: Int) {
        viewModelScope.launch {
            password.value = passDao.loadById(id)
        }
    }
    fun deletePassword(id: Int?){
         val passId: Int = id ?: error("ID cannot be null")
        viewModelScope.launch {
            passDao.deleteById(passId)
            _events.emit(UiEvent.NavigateToPasswordList)
        }
    }
    fun toEditPassword(id: Int){
        viewModelScope.launch {
            _events.emit(UiEvent.NavigateToEditPassword(id))
        }
    }
}