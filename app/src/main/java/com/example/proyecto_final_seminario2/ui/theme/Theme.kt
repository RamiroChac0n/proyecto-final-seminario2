package com.example.proyecto_final_seminario2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LocalPrimarySoft,
    onPrimary = LocalPrimaryDark,
    secondary = LocalAccentSoft,
    onSecondary = LocalPrimaryDark,
    tertiary = LocalAccent,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFE5E7EB),
    surface = Color(0xFF111827),
    onSurface = Color(0xFFE5E7EB),
    outline = Color(0xFF334155),
    error = LocalDanger
)

private val LightColorScheme = lightColorScheme(
    primary = LocalPrimary,
    onPrimary = Color.White,
    primaryContainer = LocalPrimarySoft,
    onPrimaryContainer = LocalPrimaryDark,
    secondary = LocalAccent,
    onSecondary = Color.White,
    secondaryContainer = LocalAccentSoft,
    onSecondaryContainer = Color(0xFF064E4B),
    tertiary = LocalWarning,
    background = LocalBackground,
    onBackground = LocalTextPrimary,
    surface = LocalSurface,
    onSurface = LocalTextPrimary,
    outline = LocalBorder,
    error = LocalDanger
)

@Composable
fun Proyectofinalseminario2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
