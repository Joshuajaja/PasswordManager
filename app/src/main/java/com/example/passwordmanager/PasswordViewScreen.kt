package com.example.passwordmanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordEntity

@Composable
fun PasswordViewScreen(navController: NavController){
    val app = LocalContext.current.applicationContext
            as PasswordManagerApp
    val password: PasswordEntity = PasswordEntity(2,"2","3")
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: PasswordViewViewModel = viewModel(
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
    Surface {
        Box(
            modifier = Modifier.fillMaxSize()) {
            Column() {
                Text(text = password.name)
                Text(text = password.password)
            }
        }
    }
}