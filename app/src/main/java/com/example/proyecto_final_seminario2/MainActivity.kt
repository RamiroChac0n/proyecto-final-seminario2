package com.example.proyecto_final_seminario2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyecto_final_seminario2.ui.theme.Proyectofinalseminario2Theme
import com.example.proyecto_final_seminario2.ui.login.LoginScreen
import com.example.proyecto_final_seminario2.ui.login.LoginViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyectofinalseminario2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val vm = LoginViewModel()
                    val uiState by vm.uiState.collectAsState()

                    LoginScreen(
                        state = uiState,
                        onEmailChange = vm::onEmailChange,
                        onPasswordChange = vm::onPasswordChange,
                        onLoginClick = vm::onLoginClick,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Proyectofinalseminario2Theme {
        Greeting("Android")
    }
}