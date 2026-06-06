package com.recifeemalerta.app.feature.auth.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.recifeemalerta.app.feature.auth.domain.model.AuthResult
import com.recifeemalerta.app.feature.auth.domain.model.LoginRequest
import com.recifeemalerta.app.feature.auth.domain.model.RecoveryRequest
import com.recifeemalerta.app.feature.auth.domain.model.RegisterRequest
import com.recifeemalerta.app.feature.auth.domain.model.SocialAuthProvider
import com.recifeemalerta.app.feature.auth.domain.repository.AuthRepository
import com.recifeemalerta.app.feature.auth.domain.validation.AuthValidator

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

    suspend fun submit(): Boolean {
        if (!state.canSubmit) return false

        state = state.copy(isLoading = true, feedback = null)
        return when (val result = repository.login(LoginRequest(state.email.trim(), state.password))) {
            is AuthResult.Success -> {
                state = state.copy(isLoading = false)
                true
            }
            is AuthResult.Failure -> {
                state = state.copy(isLoading = false, feedback = result.error.message)
                false
            }
        }
    }

    suspend fun loginWithProvider(provider: SocialAuthProvider): Boolean {
        state = state.copy(isLoading = true, feedback = null)
        return when (val result = repository.loginWithProvider(provider)) {
            is AuthResult.Success -> {
                state = state.copy(isLoading = false)
                true
            }
            is AuthResult.Failure -> {
                state = state.copy(isLoading = false, feedback = result.error.message)
                false
            }
        }
    }

    fun dismissFeedback() {
        state = state.copy(feedback = null)
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

    suspend fun submit(): Boolean {
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

        state = state.copy(isLoading = true, feedback = null)
        return when (
            val result = repository.register(
                RegisterRequest(
                    name = state.name.trim(),
                    email = state.email.trim(),
                    password = state.password
                )
            )
        ) {
            is AuthResult.Success -> {
                state = state.copy(isLoading = false)
                true
            }
            is AuthResult.Failure -> {
                state = state.copy(isLoading = false, feedback = result.error.message)
                false
            }
        }
    }

    suspend fun registerWithProvider(provider: SocialAuthProvider): Boolean {
        state = state.copy(isLoading = true, feedback = null)
        return when (val result = repository.loginWithProvider(provider)) {
            is AuthResult.Success -> {
                state = state.copy(isLoading = false)
                true
            }
            is AuthResult.Failure -> {
                state = state.copy(isLoading = false, feedback = result.error.message)
                false
            }
        }
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

    suspend fun submit() {
        val email = AuthValidator.email(state.email)
        if (!email.isValid) {
            state = state.copy(feedback = email.message)
            return
        }

        state = state.copy(isLoading = true, feedback = null)
        when (val result = repository.requestPasswordRecovery(RecoveryRequest(state.email.trim()))) {
            is AuthResult.Success -> {
                state = state.copy(
                    isLoading = false,
                    feedback = "Enviamos as instruções para ${state.email.trim()}."
                )
            }
            is AuthResult.Failure -> {
                state = state.copy(isLoading = false, feedback = result.error.message)
            }
        }
    }

    fun dismissFeedback() {
        state = state.copy(feedback = null)
    }
}
