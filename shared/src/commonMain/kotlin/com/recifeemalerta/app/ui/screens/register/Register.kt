package com.recifeemalerta.app.ui.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.di.LocalAppDependencies
import com.recifeemalerta.app.feature.auth.ui.state.RegisterStateHolder
import com.recifeemalerta.app.navigation.Screen
import com.recifeemalerta.app.ui.components.AppButton
import com.recifeemalerta.app.ui.components.AppButtonStyle
import com.recifeemalerta.app.ui.screens.auth.components.AuthDivider
import com.recifeemalerta.app.ui.screens.auth.components.AuthEmailField
import com.recifeemalerta.app.ui.screens.auth.components.AuthFeedback
import com.recifeemalerta.app.ui.screens.auth.components.AuthFooterLink
import com.recifeemalerta.app.ui.screens.auth.components.AuthNameField
import com.recifeemalerta.app.ui.screens.auth.components.AuthPasswordField
import com.recifeemalerta.app.ui.screens.auth.components.AuthScreenScaffold
import com.recifeemalerta.app.ui.screens.auth.components.AuthTitle
import com.recifeemalerta.app.ui.screens.auth.components.SocialAuthButtons
import com.recifeemalerta.app.ui.theme.AppColors
import com.recifeemalerta.app.ui.preview.AppPreview
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.authRepository) {
        RegisterStateHolder(dependencies.authRepository)
    }
    val state = stateHolder.state
    val coroutineScope = rememberCoroutineScope()

    AuthScreenScaffold(showBack = true, onBack = onBack) {
        AuthTitle(
            title = "Criar conta",
            subtitle = "Junte-se à nossa comunidade e construa o futuro do Recife."
        )

        Spacer(modifier = Modifier.height(48.dp))

        AuthFeedback(
            message = state.feedback,
            onClose = stateHolder::dismissFeedback
        )

        AuthNameField(
            value = state.name,
            onValueChange = stateHolder::onNameChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthEmailField(
            value = state.email,
            onValueChange = stateHolder::onEmailChanged,
            placeholder = "Seu melhor e-mail"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthPasswordField(
            value = state.password,
            onValueChange = stateHolder::onPasswordChanged,
            label = "Senha",
            placeholder = "Senha",
            visible = state.showPassword,
            onVisibilityToggle = stateHolder::togglePasswordVisibility,
            supportingText = state.passwordError ?: "Use pelo menos 6 caracteres.",
            isError = state.passwordError != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthPasswordField(
            value = state.confirmPassword,
            onValueChange = stateHolder::onConfirmPasswordChanged,
            label = "Confirmar senha",
            placeholder = "Confirmar senha",
            visible = state.showConfirmPassword,
            onVisibilityToggle = stateHolder::toggleConfirmPasswordVisibility,
            supportingText = state.confirmPasswordError,
            isError = state.confirmPasswordError != null
        )

        Spacer(modifier = Modifier.height(12.dp))

        TermsRow(
            accepted = state.acceptedTerms,
            onAcceptedChange = stateHolder::onTermsChanged
        )

        Spacer(modifier = Modifier.height(24.dp))

        AppButton(
            text = "Cadastrar",
            style = AppButtonStyle.Blue,
            enabled = state.canSubmit,
            isLoading = state.isLoading,
            onClick = {
                coroutineScope.launch {
                    if (stateHolder.submit()) {
                        onNavigate(Screen.NeighborhoodSelection)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        AuthDivider(text = " ou registrar-se com ")

        Spacer(modifier = Modifier.height(32.dp))

        SocialAuthButtons(
            googleText = "Registrar-se com Google",
            appleText = "Registrar-se com Apple",
            onProviderClick = {
                coroutineScope.launch {
                    if (stateHolder.registerWithProvider(it)) {
                        onNavigate(Screen.NeighborhoodSelection)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthFooterLink(
            text = "Já tem uma conta? ",
            actionText = "Faça Login",
            onClick = { onNavigate(Screen.Login) },
            boldAction = true
        )
    }
}

@Composable
private fun TermsRow(
    accepted: Boolean,
    onAcceptedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = accepted,
            onCheckedChange = onAcceptedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = AppColors.ButtonBlue,
                uncheckedColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
        )

        val annotatedString = buildAnnotatedString {
            append("Eu aceito os ")
            withStyle(
                style = SpanStyle(
                    color = AppColors.ButtonBlue,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("termos de uso")
            }
            append(" e ")
            withStyle(
                style = SpanStyle(
                    color = AppColors.ButtonBlue,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("privacidade")
            }
        }

        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.clickable { }
        )
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    AppPreview {
        RegisterScreen(onNavigate = {}, onBack = {})
    }
}
