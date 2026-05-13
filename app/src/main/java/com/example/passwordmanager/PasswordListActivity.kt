package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordmanager.ui.theme.PasswordManagerTheme

class PasswordListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                val viewModel: PasswordListViewModel = viewModel()
                Surface(modifier = Modifier.fillMaxSize()) {
                }
            }
        }
    }
}

@Composable
fun PasswordListScreen(){

}