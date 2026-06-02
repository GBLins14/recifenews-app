package com.recifenews.app.ui.screens.recovery

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recifenews.app.di.LocalAppDependencies
import com.recifenews.app.feature.auth.ui.state.RecoveryStateHolder
import com.recifenews.app.navigation.Screen
import com.recifenews.app.ui.components.AppButton
import com.recifenews.app.ui.components.AppButtonStyle
import com.recifenews.app.ui.screens.auth.components.AuthEmailField
import com.recifenews.app.ui.screens.auth.components.AuthFeedback
import com.recifenews.app.ui.screens.auth.components.AuthScreenScaffold
import com.recifenews.app.ui.screens.auth.components.AuthTitle
import com.recifenews.app.ui.preview.AppPreview
import kotlinx.coroutines.launch

@Composable
fun RecoveryScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.authRepository) {
        RecoveryStateHolder(dependencies.authRepository)
    }
    val state = stateHolder.state
    val coroutineScope = rememberCoroutineScope()

    AuthScreenScaffold(showBack = true, onBack = onBack) {
        AuthTitle(
            title = "Recuperar senha",
            subtitle = "Não se preocupe. Insira seu e-mail e enviaremos as instruções para você."
        )

        Spacer(modifier = Modifier.height(56.dp))

        AuthFeedback(
            message = state.feedback,
            onClose = stateHolder::dismissFeedback
        )

        AuthEmailField(
            value = state.email,
            onValueChange = stateHolder::onEmailChanged
        )

        Spacer(modifier = Modifier.height(32.dp))

        AppButton(
            text = "Enviar instruções",
            style = AppButtonStyle.Blue,
            enabled = state.canSubmit,
            isLoading = state.isLoading,
            onClick = {
                coroutineScope.launch {
                    stateHolder.submit()
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun RecoveryScreenPreview() {
    AppPreview {
        RecoveryScreen(onNavigate = {}, onBack = {})
    }
}
