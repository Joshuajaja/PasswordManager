package com.example.passwordmanager

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun EditPasswordScreen(navController: NavController, id: Int){
    val app = androidx.compose.ui.platform.LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: EditPasswordViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(dao, passwordDao))
    val dbPassword by viewModel.password.collectAsState()

    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        viewModel.loadPassword(id)
    }

    LaunchedEffect(dbPassword) {
        dbPassword?.let {
            name = it.name
            password = it.password
        }
    }
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
                is UiEvent.NavigateToNewPass -> {
                    navController.navigate(
                        UiEvent.NavigateToNewPass(event.name, event.genPass)
                    ) {
                        launchSingleTop = true
                    }
                }
                is UiEvent.NavigateToPasswordView -> {
                    navController.navigate(
                        PasswordViewScreenRoute(event.id)
                    ) {
                        launchSingleTop = true
                    }
                }
                is UiEvent.NavigateToEditPassword -> {
                    navController.navigate(
                        EditPasswordScreenRoute(event.id)
                    ) {
                        launchSingleTop = true
                    }
                }
                is UiEvent.NavigateToPasswordGen -> {
                    navController.navigate(
                        PasswordGenScreenRoute(event.name)
                    ) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }


    Surface {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Column() {
            TextField(
                label = { Text("Name") },
                value = name,
                onValueChange = { name = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ), keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.viewModelScope.launch {
                            viewModel.updatePass(id,name, password)
                        }}
                ))
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text("Password") },
                value = password,
                onValueChange = {password = it},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go
                ), keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.viewModelScope.launch {
                            viewModel.updatePass(id,name, password)
                        }}
                )
            )
            }
        }
    }
}