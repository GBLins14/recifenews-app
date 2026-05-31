package com.recifenews.app.feature.auth.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.recifenews.app.feature.auth.domain.model.LoginRequest
import com.recifenews.app.feature.auth.domain.model.RecoveryRequest
import com.recifenews.app.feature.auth.domain.model.RegisterRequest
import com.recifenews.app.feature.auth.domain.model.SocialAuthProvider
import com.recifenews.app.feature.auth.domain.repository.AuthRepository
import com.recifenews.app.feature.auth.domain.validation.AuthValidator

class LoginStateHolder(
    private val repository: AuthRepository
) {
    var state by mutableStateOf(LoginUiState())
        private set

    fun onEmailChanged(value: String) {
        state = state.copy(email = value)
    }

    fun onPasswordChanged(value: String) {
        state = state.copy(password = value)
    }

    fun togglePasswordVisibility() {
        state = state.copy(showPassword = !state.showPassword)
    }

    fun submit(): Boolean {
        if (!state.canSubmit) return false
        repository.login(LoginRequest(state.email, state.password))
        return true
    }

    fun loginWithProvider(provider: SocialAuthProvider): Boolean {
        repository.loginWithProvider(provider)
        return true
    }
}

class RegisterStateHolder(
    private val repository: AuthRepository
) {
    var state by mutableStateOf(RegisterUiState())
        private set

    fun onNameChanged(value: String) {
        state = state.copy(name = value)
    }

    fun onEmailChanged(value: String) {
        state = state.copy(email = value)
    }

    fun onPasswordChanged(value: String) {
        state = state.copy(password = value).withPasswordValidation()
    }

    fun onConfirmPasswordChanged(value: String) {
        state = state.copy(confirmPassword = value).withPasswordValidation()
    }

    fun onTermsChanged(accepted: Boolean) {
        state = state.copy(acceptedTerms = accepted)
    }

    fun togglePasswordVisibility() {
        state = state.copy(showPassword = !state.showPassword)
    }

    fun toggleConfirmPasswordVisibility() {
        state = state.copy(showConfirmPassword = !state.showConfirmPassword)
    }

    fun submit(): Boolean {
        val name = AuthValidator.name(state.name)
        val email = AuthValidator.email(state.email)
        val password = AuthValidator.password(state.password)
        val confirmation = AuthValidator.confirmPassword(state.password, state.confirmPassword)

        state = state.copy(
            passwordError = password.message,
            confirmPasswordError = confirmation.message,
            feedback = name.message ?: email.message
        )

        if (!state.canSubmit || !name.isValid || !email.isValid || !password.isValid || !confirmation.isValid) {
            return false
        }

        repository.register(
            RegisterRequest(
                name = state.name,
                email = state.email,
                password = state.password
            )
        )
        return true
    }

    fun registerWithProvider(provider: SocialAuthProvider): Boolean {
        repository.loginWithProvider(provider)
        return true
    }

    fun dismissFeedback() {
        state = state.copy(feedback = null)
    }

    private fun RegisterUiState.withPasswordValidation(): RegisterUiState {
        val passwordMessage = if (password.isBlank() || password.length >= 6) null else AuthValidator.password(password).message
        val confirmationMessage = if (confirmPassword.isBlank()) null else AuthValidator.confirmPassword(password, confirmPassword).message
        return copy(
            passwordError = passwordMessage,
            confirmPasswordError = confirmationMessage
        )
    }
}

class RecoveryStateHolder(
    private val repository: AuthRepository
) {
    var state by mutableStateOf(RecoveryUiState())
        private set

    fun onEmailChanged(value: String) {
        state = state.copy(email = value)
    }

    fun submit() {
        val email = AuthValidator.email(state.email)
        if (!email.isValid) {
            state = state.copy(feedback = email.message)
            return
        }

        repository.requestPasswordRecovery(RecoveryRequest(state.email))
        state = state.copy(feedback = "Enviamos as instruções para ${state.email.trim()}.")
    }

    fun dismissFeedback() {
        state = state.copy(feedback = null)
    }
}
