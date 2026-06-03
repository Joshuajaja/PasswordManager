package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
@Serializable
data class NewPassScreenRoute(val name: String, val genPass: String)
@Serializable
data class PasswordViewScreenRoute(val id: Int)
@Serializable
data class EditPasswordScreenRoute(val id: Int)
@Serializable
data class PasswordGenScreenRoute(val name: String)
sealed class UiEvent {
    data object NavigateToNewPin : UiEvent()
    data object NavigateToPassword : UiEvent()
    data object NavigateToPasswordList : UiEvent()
    data class NavigateToNewPass(val name: String, val genPass: String) : UiEvent()
    data class NavigateToPasswordGen(val name: String) : UiEvent()
    data class NavigateToPasswordView(val id: Int) : UiEvent()
    data class NavigateToEditPassword(val id: Int) : UiEvent()
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
        composable<NewPassScreenRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<NewPassScreenRoute>()
            NewPassScreen(navController=navController, route.name, route.genPass)
        }
        composable<PasswordViewScreenRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<PasswordViewScreenRoute>()
            PasswordViewScreen(navController=navController, route.id)
        }
        composable<EditPasswordScreenRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<EditPasswordScreenRoute>()
            EditPasswordScreen(navController=navController, route.id)
        }
        composable<PasswordGenScreenRoute>{ backStackEntry ->
            val route = backStackEntry.toRoute<PasswordGenScreenRoute>()
            PasswordGenScreen(navController=navController, route.name)
        }
    }
}






