package com.example.passwordmanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController

@Composable
fun NewPinScreen(navController: NavController){
    val app = androidx.compose.ui.platform.LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: NewPinViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
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
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Create new PIN",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 37.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                var text by remember { mutableStateOf("") }
                TextField(
                    value = text,
                    onValueChange = {
                        text = it.filter { c -> c.isDigit() }.take(5)
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.width(120.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = {
                            viewModel.newPin(text)
                        }
                    )
                )
            }
        }
    }
}