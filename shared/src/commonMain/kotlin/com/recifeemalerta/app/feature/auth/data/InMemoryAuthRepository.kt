package com.recifeemalerta.app.feature.auth.data

import com.recifeemalerta.app.feature.auth.domain.model.AuthResult
import com.recifeemalerta.app.feature.auth.domain.model.AuthSession
import com.recifeemalerta.app.feature.auth.domain.model.AuthUser
import com.recifeemalerta.app.feature.auth.domain.model.LoginRequest
import com.recifeemalerta.app.feature.auth.domain.model.RecoveryRequest
import com.recifeemalerta.app.feature.auth.domain.model.RegisterRequest
import com.recifeemalerta.app.feature.auth.domain.model.SocialAuthProvider
import com.recifeemalerta.app.feature.auth.domain.repository.AuthRepository

class InMemoryAuthRepository : AuthRepository {
    override suspend fun login(request: LoginRequest): AuthResult<AuthSession> {
        return AuthResult.Success(
            sessionFor(
                user = AuthUser(
                    id = "user-${request.email.trim().lowercase()}",
                    name = "João Silva",
                    email = request.email.trim()
                )
            )
        )
    }

    override suspend fun register(request: RegisterRequest): AuthResult<AuthSession> {
        return AuthResult.Success(
            sessionFor(
                user = AuthUser(
                    id = "user-${request.email.trim().lowercase()}",
                    name = request.name.trim(),
                    email = request.email.trim()
                )
            )
        )
    }

    override suspend fun requestPasswordRecovery(request: RecoveryRequest): AuthResult<Unit> {
        return AuthResult.Success(Unit)
    }

    override suspend fun loginWithProvider(provider: SocialAuthProvider): AuthResult<AuthSession> {
        val providerName = provider.label.lowercase()
        return AuthResult.Success(
            sessionFor(
                user = AuthUser(
                    id = "social-$providerName",
                    name = "João Silva",
                    email = "joao@$providerName.com"
                )
            )
        )
    }

    private fun sessionFor(user: AuthUser): AuthSession {
        return AuthSession(
            user = user,
            accessToken = "dev-access-token-${user.id}",
            refreshToken = "dev-refresh-token-${user.id}"
        )
    }
}
