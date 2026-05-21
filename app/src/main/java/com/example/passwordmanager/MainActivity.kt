package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecureTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.example.passwordmanager.data.AppDatabase
import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyApp()
                    }
                }
            }
        }
    }
@Serializable
object NewPinScreenRoute
@Serializable
object PasswordScreenRoute
@Serializable
object PasswordListScreenRoute
sealed class UiEvent {
    data object NavigateToNewPin : UiEvent()
    data object NavigateToPassword : UiEvent()
    data object NavigateToPasswordList : UiEvent()
}
@Composable
fun MyApp() {
    val app = LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: MyAppViewModel = viewModel(
        factory = ViewModelFactory(dao, passwordDao))
    val navController = rememberNavController()
    val passwords by viewModel.passwords.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )
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
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = PasswordScreenRoute
    ) {
        composable<PasswordScreenRoute> {
            PasswordScreen(navController = navController)
        }
        composable<NewPinScreenRoute> {
            NewPinScreen(navController = navController)
        }
        composable<PasswordListScreenRoute>{
            PasswordListScreen(navController = navController, passwords)
        }
    }
}






