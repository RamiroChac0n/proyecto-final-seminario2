package com.example.proyecto_final_seminario2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto_final_seminario2.ui.navigation.AppNavGraph
import com.example.proyecto_final_seminario2.ui.theme.Proyectofinalseminario2Theme

/**
 * Punto de entrada de la aplicacion.
 *
 * Crea las dependencias globales con [AppContainer] y monta el grafo de
 * navegacion principal dentro del tema visual de Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
<<<<<<< Updated upstream
=======
            // remember evita reconstruir repositorios y clientes externos en cada recomposicion.
            val appContainer = remember {
                AppContainer(applicationContext)
            }

>>>>>>> Stashed changes
            Proyectofinalseminario2Theme {
                AppNavGraph()
            }
        }
    }
}
