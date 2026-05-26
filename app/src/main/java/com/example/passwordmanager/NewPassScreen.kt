package com.example.passwordmanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun NewPassScreen(navController: NavController){
    val app = LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: NewPassViewModel = viewModel(
        factory = ViewModelFactory(dao, passwordDao))

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {

                UiEvent.NavigateToNewPin -> {
                    navController.navigate(NewPinScreenRoute) {
                        popUpTo(PasswordScreenRoute) { inclusive = true }
                    }
                }

                UiEvent.NavigateToPassword -> {
                    navController.navigate(PasswordScreenRoute) {
                        popUpTo(NewPinScreenRoute) { inclusive = true }
                    }
                }

                UiEvent.NavigateToPasswordList -> {
                    navController.navigate(PasswordListScreenRoute) {
                        launchSingleTop = true
                    }
                }
                UiEvent.NavigateToNewPass -> {
                    navController.navigate(NewPassScreenRoute) {
                        launchSingleTop = true
                    }
                }
                UiEvent.NavigateToPasswordView -> {
                    navController.navigate(PasswordViewScreenRoute) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val secondFocusRequester = remember { FocusRequester() }
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column() {
            TextField(
                label = { Text("Name") },
                value = name,
                onValueChange = { name = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ), keyboardActions = KeyboardActions(
                    onNext = {
                        secondFocusRequester.requestFocus()
                    }
                ))
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text("Password") },
                modifier = Modifier.focusRequester(secondFocusRequester),
                value = password,
                onValueChange = {password = it},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go
                ), keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.viewModelScope.launch {
                        viewModel.newPass(name, password)
                    }}
                )
            )
        }
        }
    }
}