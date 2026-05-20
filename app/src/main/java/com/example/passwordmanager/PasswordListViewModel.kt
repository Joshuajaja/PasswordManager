package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PinDao
import kotlinx.coroutines.launch

class PasswordListViewModel (private val dao: PinDao) : ViewModel() {

}