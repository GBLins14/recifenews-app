package com.recifenews.app.ui.preview

import androidx.compose.runtime.Composable
import com.recifenews.app.di.AppDependencies
import com.recifenews.app.di.ProvideAppDependencies
import com.recifenews.app.ui.theme.AppTheme

@Composable
fun AppPreview(content: @Composable () -> Unit) {
    AppTheme {
        ProvideAppDependencies(dependencies = AppDependencies()) {
            content()
        }
    }
}
