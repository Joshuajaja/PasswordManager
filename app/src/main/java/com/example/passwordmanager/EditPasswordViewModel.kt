package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.EncryptionData
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.data.cipherString
import com.example.passwordmanager.data.decryptString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPasswordViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events
    val password = MutableStateFlow<PasswordEntity?>(null)
    suspend fun updatePass(inputId: Int, name: String, password: String){
        if(name == "" || password == ""){_events.emit(UiEvent.NavigateToNewPass)}
        else{
            val encryption: EncryptionData = cipherString(password)
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    passDao.updateById(inputId,name,encryption.ciphertext,encryption.iv) }
                _events.emit(UiEvent.NavigateToPasswordList)
            }
        }
    }
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
}
