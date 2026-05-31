package com.recifenews.app.feature.auth.domain.model

enum class SocialAuthProvider(val label: String) {
    Google("Google"),
    Apple("Apple")
}

data class AuthUser(
    val id: String,
    val name: String,
    val email: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RecoveryRequest(
    val email: String
)

data class AuthValidation(
    val isValid: Boolean,
    val message: String? = null
)
