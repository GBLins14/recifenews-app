package com.recifenews.app.feature.auth.data

import com.recifenews.app.feature.auth.domain.model.AuthUser
import com.recifenews.app.feature.auth.domain.model.LoginRequest
import com.recifenews.app.feature.auth.domain.model.RecoveryRequest
import com.recifenews.app.feature.auth.domain.model.RegisterRequest
import com.recifenews.app.feature.auth.domain.model.SocialAuthProvider
import com.recifenews.app.feature.auth.domain.repository.AuthRepository

class InMemoryAuthRepository : AuthRepository {
    override fun login(request: LoginRequest): AuthUser {
        return AuthUser(
            id = "user-${request.email.trim().lowercase()}",
            name = "João Silva",
            email = request.email.trim()
        )
    }

    override fun register(request: RegisterRequest): AuthUser {
        return AuthUser(
            id = "user-${request.email.trim().lowercase()}",
            name = request.name.trim(),
            email = request.email.trim()
        )
    }

    override fun requestPasswordRecovery(request: RecoveryRequest): Boolean {
        return request.email.isNotBlank()
    }

    override fun loginWithProvider(provider: SocialAuthProvider): AuthUser {
        val providerName = provider.label.lowercase()
        return AuthUser(
            id = "social-$providerName",
            name = "João Silva",
            email = "joao@$providerName.com"
        )
    }
}
