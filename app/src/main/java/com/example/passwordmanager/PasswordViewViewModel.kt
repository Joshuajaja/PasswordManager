package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import com.example.passwordmanager.data.PasswordDao

class PasswordViewViewModel(private val passDao: PasswordDao) : ViewModel() {
    private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
    val events = _events


}