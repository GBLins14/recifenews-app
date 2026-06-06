package com.recifeemalerta.app.feature.auth.ui.state

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val feedback: String? = null
) {
    val canSubmit: Boolean
        get() = email.isNotBlank() && password.isNotBlank()
}

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptedTerms: Boolean = false,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val feedback: String? = null
) {
    val canSubmit: Boolean
        get() = acceptedTerms &&
            name.isNotBlank() &&
            email.isNotBlank() &&
            password.length >= 6 &&
            password == confirmPassword
}

data class RecoveryUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val feedback: String? = null
) {
    val canSubmit: Boolean
        get() = email.isNotBlank()
}
