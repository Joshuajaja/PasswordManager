package com.example.passwordmanager

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordEntity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordViewScreen(navController: NavController, id: Int){
    val app = LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: PasswordViewViewModel = viewModel(
        factory = ViewModelFactory(dao, passwordDao))
    val password by viewModel.password.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadPassword(id)
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
                UiEvent.NavigateToNewPass -> {
                    navController.navigate(NewPassScreenRoute) {
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
            }
        }
    }
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column() {
                Text(text = password?.name ?: String(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp))
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = password?.password ?: String(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp))
            }
            val passwordId: Int? = password?.uid
            Button(modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomEnd),
                onClick = {viewModel.deletePassword(passwordId) }){
                Text(text = "-",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 37.sp))
            }
            Button(modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomStart),
                onClick = {viewModel.toEditPassword(id) }){
                Text(text = "✏\uFE0F",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 23.sp))
            }
        }
    }
}