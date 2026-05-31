package com.recifenews.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = AppColors.BrandOnPrimary,
    primaryContainer = AppColors.BrandPrimaryContainer,
    onPrimaryContainer = BrandPrimary,
    
    secondary = BrandSecondary,
    onSecondary = LightTextPrimary,
    
    background = LightBackground,
    onBackground = LightTextPrimary,
    
    surface = LightSurface,
    onSurface = LightTextPrimary,
    
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    
    outline = LightOutline,
    error = AppColors.Error,
    onError = AppColors.White
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimaryLight,
    onPrimary = AppColors.BrandOnPrimaryDark,
    primaryContainer = BrandPrimary,
    onPrimaryContainer = AppColors.White,
    
    secondary = BrandSecondary,
    onSecondary = AppColors.Black,
    
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    
    outline = DarkOutline,
    error = AppColors.ErrorContainer,
    onError = AppColors.OnErrorContainer
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
