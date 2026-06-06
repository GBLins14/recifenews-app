package com.recifeemalerta.app.feature.auth.domain.validation

import com.recifeemalerta.app.feature.auth.domain.model.AuthValidation

object AuthValidator {
    fun email(email: String): AuthValidation {
        val value = email.trim()
        return when {
            value.isBlank() -> AuthValidation(false)
            "@" !in value || "." !in value.substringAfter("@", "") -> {
                AuthValidation(false, "Informe um e-mail válido.")
            }
            else -> AuthValidation(true)
        }
    }

    fun password(password: String): AuthValidation {
        return when {
            password.isBlank() -> AuthValidation(false)
            password.length < 6 -> AuthValidation(false, "Use pelo menos 6 caracteres.")
            else -> AuthValidation(true)
        }
    }

    fun name(name: String): AuthValidation {
        return when {
            name.trim().isBlank() -> AuthValidation(false)
            name.trim().length < 3 -> AuthValidation(false, "Informe seu nome completo.")
            else -> AuthValidation(true)
        }
    }

    fun confirmPassword(password: String, confirmation: String): AuthValidation {
        return when {
            confirmation.isBlank() -> AuthValidation(false)
            password != confirmation -> AuthValidation(false, "As senhas precisam ser iguais.")
            else -> AuthValidation(true)
        }
    }
}
