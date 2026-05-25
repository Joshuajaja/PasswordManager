package com.example.passwordmanager

import android.graphics.fonts.FontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanager.data.PasswordEntity

@Composable
fun PasswordListScreen(navController: NavController, passwords: List<PasswordEntity>){
    val app = androidx.compose.ui.platform.LocalContext.current.applicationContext
            as PasswordManagerApp
    val dao = app.db.pinDao()
    val passwordDao = app.db.PasswordDao()
    val viewModel: PasswordListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
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
            }
        }
    }


    Surface {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.safeDrawingPadding()) {
                items(passwords){
                    password -> PasswordItem(password)
                }
            }
        Surface(shape = MaterialTheme.shapes.medium) { }
        Button(modifier = Modifier
            .size(60.dp)
            .align(Alignment.BottomEnd),
            onClick = { viewModel.toNewPasswordPage() }){
            Text(text = "+",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 37.sp))
        }
      }
    }
}

@Composable
fun PasswordItem(password: PasswordEntity) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 8.dp).fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.folder_document_file_format_svgrepo_com),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Column{Text(
                text = password.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 26.sp)
            )
        }
    }
}

@Preview
@Composable
fun PasswordItemPreview(){
    PasswordItem(PasswordEntity(2,"Google","pass"))
}
