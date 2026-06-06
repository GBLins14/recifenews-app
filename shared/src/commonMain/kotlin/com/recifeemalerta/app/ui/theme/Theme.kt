package com.recifeemalerta.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,

    secondary = LightSecondary,
    onSecondary = LightOnSecondary,

    background = LightBackground,
    onBackground = LightOnBackground,

    surface = LightSurface,
    onSurface = LightOnSurface,

    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,

    outline = LightBorder,
    outlineVariant = LightDivider,

    error = Error,
    onError = LightOnPrimary,

    inverseSurface = DarkSurface,
    inverseOnSurface = DarkOnSurface,
    inversePrimary = DarkPrimary,

    scrim = LightScrim
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,

    background = DarkBackground,
    onBackground = DarkOnBackground,

    surface = DarkSurface,
    onSurface = DarkOnSurface,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,

    outline = DarkBorder,
    outlineVariant = DarkDivider,

    error = Error,
    onError = DarkOnPrimary,

    inverseSurface = LightSurface,
    inverseOnSurface = LightOnSurface,
    inversePrimary = LightPrimary,

    scrim = DarkScrim
)

// Premium Shapes
private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = AppShapes,
        content = content
    )
}

@Composable
fun RecifeEmAlertaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    AppTheme(darkTheme = darkTheme, content = content)
}

data class ThemeController(
    val isDarkTheme: Boolean,
    val onToggleDarkTheme: () -> Unit
)

val LocalThemeController = staticCompositionLocalOf {
    ThemeController(
        isDarkTheme = false,
        onToggleDarkTheme = {}
    )
}

@Composable
fun ProvideThemeController(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalThemeController provides ThemeController(
            isDarkTheme = isDarkTheme,
            onToggleDarkTheme = onToggleDarkTheme
        )
    ) {
        content()
    }
}
