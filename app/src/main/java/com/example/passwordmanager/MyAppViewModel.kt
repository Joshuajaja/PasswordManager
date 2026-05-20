package com.example.passwordmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PinDao
import kotlinx.coroutines.launch

    class MyAppViewModel(private val dao: PinDao) : ViewModel() {

        private val _events = kotlinx.coroutines.flow.MutableSharedFlow<UiEvent>()
        val events = _events

        init {
            checkIfPinExist()
        }
        fun checkIfPinExist(){
            viewModelScope.launch {
                val hasPin = dao.hasAnyPin()

                if (!hasPin){
                    _events.emit(UiEvent.NavigateToNewPin)
                }
            }
        }
    }