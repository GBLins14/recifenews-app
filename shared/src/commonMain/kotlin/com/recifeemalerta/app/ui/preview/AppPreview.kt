package com.recifeemalerta.app.ui.preview

import androidx.compose.runtime.Composable
import com.recifeemalerta.app.di.AppDependencies
import com.recifeemalerta.app.di.ProvideAppDependencies
import com.recifeemalerta.app.ui.theme.AppTheme
import com.recifeemalerta.app.ui.theme.ProvideThemeController

@Composable
fun AppPreview(content: @Composable () -> Unit) {
    AppTheme(darkTheme = false) {
        ProvideThemeController(isDarkTheme = false, onToggleDarkTheme = {}) {
            ProvideAppDependencies(dependencies = AppDependencies()) {
                content()
            }
        }
    }
}
