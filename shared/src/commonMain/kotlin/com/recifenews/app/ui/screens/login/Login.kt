package com.recifenews.app.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recifenews.app.di.LocalAppDependencies
import com.recifenews.app.feature.auth.ui.state.LoginStateHolder
import com.recifenews.app.navigation.Screen
import com.recifenews.app.ui.components.AppButton
import com.recifenews.app.ui.components.AppButtonStyle
import com.recifenews.app.ui.screens.auth.components.AuthDivider
import com.recifenews.app.ui.screens.auth.components.AuthEmailField
import com.recifenews.app.ui.screens.auth.components.AuthFooterLink
import com.recifenews.app.ui.screens.auth.components.AuthPasswordField
import com.recifenews.app.ui.screens.auth.components.AuthScreenScaffold
import com.recifenews.app.ui.screens.auth.components.AuthTitle
import com.recifenews.app.ui.screens.auth.components.SocialAuthButtons
import com.recifenews.app.ui.theme.AppColors
import com.recifenews.app.ui.preview.AppPreview

@Composable
fun LoginScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.authRepository) {
        LoginStateHolder(dependencies.authRepository)
    }
    val state = stateHolder.state

    AuthScreenScaffold(showBack = false, onBack = onBack) {
        AuthTitle(
            title = "Login",
            subtitle = "Bem-vindo de volta!"
        )

        Spacer(modifier = Modifier.height(56.dp))

        AuthEmailField(
            value = state.email,
            onValueChange = stateHolder::onEmailChanged,
            placeholder = "E-mail ou telefone"
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthPasswordField(
            value = state.password,
            onValueChange = stateHolder::onPasswordChanged,
            label = "Senha",
            placeholder = "Senha",
            visible = state.showPassword,
            onVisibilityToggle = stateHolder::togglePasswordVisibility
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { onNavigate(Screen.Recovery) },
                modifier = Modifier.offset(x = 12.dp)
            ) {
                Text(
                    text = "Esqueci minha senha",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal),
                    color = AppColors.ButtonBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AppButton(
            text = "Entrar",
            style = AppButtonStyle.Blue,
            enabled = state.canSubmit,
            onClick = {
                if (stateHolder.submit()) {
                    onNavigate(Screen.Home)
                }
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        AuthDivider(text = " ou continue com ")

        Spacer(modifier = Modifier.height(32.dp))

        SocialAuthButtons(
            googleText = "Continuar com Google",
            appleText = "Continuar com Apple",
            onProviderClick = {
                if (stateHolder.loginWithProvider(it)) {
                    onNavigate(Screen.Home)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthFooterLink(
            text = "Não tem uma conta? ",
            actionText = "Cadastre-se",
            onClick = { onNavigate(Screen.Register) }
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppPreview {
        LoginScreen(onNavigate = {}, onBack = {})
    }
}
