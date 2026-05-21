package com.example.passwordmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager.data.PasswordEntity

@Composable
fun PasswordListScreen(navController: NavController, passwords: List<PasswordEntity>){
    val app = androidx.compose.ui.platform.LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: PasswordListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(dao, passwordDao))
    val passwordList = passwords

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


    Surface {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(passwordList){
                    password -> PasswordItem(password)
                }
            }
        }
    }
}

@Composable
fun PasswordItem(password: PasswordEntity) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.folder_document_file_format_svgrepo_com),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Column{Text(
                text = password.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun PasswordItemPreview(){
    PasswordItem(PasswordEntity(2,"Google","pass"))
}