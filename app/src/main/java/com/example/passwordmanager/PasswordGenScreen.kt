package com.example.passwordmanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun PasswordGenScreen(navController: NavController, name: String){
    val app = LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: PasswordGenViewModel = viewModel(
        factory = ViewModelFactory(dao, passwordDao))
    viewModel.getName(name)

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
                        NewPassScreenRoute(event.name, event.genPass)
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
    var name by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("22") }
    val secondFocusRequester = remember { FocusRequester() }
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column() {
                Text(text = "Password generator",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp))
                TextField(
                    label = { Text("Amount of characters") },
                    modifier = Modifier.focusRequester(secondFocusRequester),
                    value = length,
                    onValueChange = {length = it},
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go
                    ), keyboardActions = KeyboardActions(
                        onGo = {
                            viewModel.viewModelScope.launch {
                                viewModel.randomPass(length.toIntOrNull())
                            }}
                    )
                )
            }
        }
    }
}