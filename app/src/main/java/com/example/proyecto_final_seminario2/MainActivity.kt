package com.example.proyecto_final_seminario2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto_final_seminario2.ui.navigation.AppNavGraph
import com.example.proyecto_final_seminario2.ui.theme.Proyectofinalseminario2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyectofinalseminario2Theme {
                AppNavGraph()
            }
        }
    }
}
