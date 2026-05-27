package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPasswordViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
    val password = MutableStateFlow<PasswordEntity?>(null)
    suspend fun UpdatePass(inputId: Int, name: String, password: String){
        if(name == "" || password == ""){_events.emit(UiEvent.NavigateToNewPass)}
        else{
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    passDao.updateById(inputId,name,password) }
                _events.emit(UiEvent.NavigateToPasswordList)
            }
        }
    }
    fun loadPassword(id: Int) {
        viewModelScope.launch {
            password.value = passDao.loadById(id)
        }
    }
}
