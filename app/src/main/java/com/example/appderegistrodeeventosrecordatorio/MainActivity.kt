package com.example.appderegistrodeeventosrecordatorio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appderegistrodeeventosrecordatorio.ui.pantallas.PantallaAutenticacion
import com.example.appderegistrodeeventosrecordatorio.ui.pantallas.PantallaPrincipal
import com.example.appderegistrodeeventosrecordatorio.ui.pantallas.PantallaRegistro
import com.example.appderegistrodeeventosrecordatorio.ui.theme.AppDeRegistroDeEventosrecordatorioTheme
import com.example.appderegistrodeeventosrecordatorio.ui.vista_modelo.RecordatorioViewModel

sealed class Destino(val ruta: String) {
    object Autenticacion : Destino("autenticacion")
    object Principal : Destino("principal")
    object Registro : Destino("registro")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val esTemaOscuroSistema = isSystemInDarkTheme()
            var temaOscuroManual by remember { mutableStateOf<Boolean?>(null) }
            val usarTemaOscuro = temaOscuroManual ?: esTemaOscuroSistema

            AppDeRegistroDeEventosrecordatorioTheme(darkTheme = usarTemaOscuro) {
                AppNavegacion(
                    esTemaOscuro = usarTemaOscuro,
                    alCambiarTema = { temaOscuroManual = !usarTemaOscuro }
                )
            }
        }
    }
}

@Composable
fun AppNavegacion(
    esTemaOscuro: Boolean,
    alCambiarTema: () -> Unit
) {
    val controladorNavegacion = rememberNavController()
    val vistaModelo: RecordatorioViewModel = viewModel()

    NavHost(
        navController = controladorNavegacion,
        startDestination = Destino.Autenticacion.ruta
    ) {
        composable(Destino.Autenticacion.ruta) {
            PantallaAutenticacion(
                esTemaOscuro = esTemaOscuro,
                alCambiarTema = alCambiarTema,
                alRegistrar = {
                    controladorNavegacion.navigate(Destino.Principal.ruta) {
                        popUpTo(Destino.Autenticacion.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(Destino.Principal.ruta) {
            PantallaPrincipal(
                vistaModelo = vistaModelo,
                esTemaOscuro = esTemaOscuro,
                alCambiarTema = alCambiarTema,
                alAgregar = { controladorNavegacion.navigate(Destino.Registro.ruta) }
            )
        }
        composable(Destino.Registro.ruta) {
            PantallaRegistro(
                esTemaOscuro = esTemaOscuro,
                alCambiarTema = alCambiarTema,
                alGuardar = { recordatorio ->
                    vistaModelo.agregarRecordatorio(recordatorio)
                    controladorNavegacion.popBackStack()
                },
                alRegresar = { controladorNavegacion.popBackStack() }
            )
        }
    }
}
