package com.example.appderegistrodeeventosrecordatorio.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MoradoPrimario,
    secondary = MoradoSecundario,
    tertiary = MoradoTerciario,
    background = MoradoFondoOscuro,
    surface = MoradoFondoOscuro,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onTertiary = TextoPrimarioClaro,
    onBackground = TextoPrimarioOscuro,
    onSurface = TextoPrimarioOscuro,
    error = ErrorRojo,
    outline = MoradoTerciario.copy(alpha = 0.5f),
    surfaceVariant = Color(0xFF1F1235)
)

private val LightColorScheme = lightColorScheme(
    primary = MoradoPrimario,
    secondary = MoradoSecundario,
    tertiary = MoradoTerciario,
    background = MoradoFondoClaro,
    surface = MoradoFondoClaro,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    onTertiary = TextoPrimarioClaro,
    onBackground = TextoPrimarioClaro,
    onSurface = TextoPrimarioClaro,
    error = ErrorRojo
)

@Composable
fun AppDeRegistroDeEventosrecordatorioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
