package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.EncryptionData
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.data.decryptString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PasswordViewViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
    val password = MutableStateFlow<PasswordEntity?>(null)
    fun loadPassword(id: Int) {
        viewModelScope.launch {
            val encryptedPassword: PasswordEntity = passDao.loadById(id)
            val unencryptionData = EncryptionData(
                encryptedPassword.iv,
                encryptedPassword.password)
            val unencryptedPassword = decryptString(unencryptionData)
            password.value = PasswordEntity(
                encryptedPassword.uid,
                encryptedPassword.name,
                unencryptedPassword,
                "Unencrypted")
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