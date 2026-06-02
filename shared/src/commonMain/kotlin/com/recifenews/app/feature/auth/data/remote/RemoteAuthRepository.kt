package com.recifenews.app.feature.auth.data.remote

import com.recifenews.app.feature.auth.domain.model.AuthError
import com.recifenews.app.feature.auth.domain.model.AuthResult
import com.recifenews.app.feature.auth.domain.model.AuthSession
import com.recifenews.app.feature.auth.domain.model.LoginRequest
import com.recifenews.app.feature.auth.domain.model.RecoveryRequest
import com.recifenews.app.feature.auth.domain.model.RegisterRequest
import com.recifenews.app.feature.auth.domain.model.SocialAuthProvider
import com.recifenews.app.feature.auth.domain.repository.AuthRepository

class RemoteAuthRepository(
    private val apiClient: AuthApiClient
) : AuthRepository {
    override suspend fun login(request: LoginRequest): AuthResult<AuthSession> {
        return runAuthRequest { apiClient.login(request) }
    }

    override suspend fun register(request: RegisterRequest): AuthResult<AuthSession> {
        return runAuthRequest { apiClient.register(request) }
    }

    override suspend fun requestPasswordRecovery(request: RecoveryRequest): AuthResult<Unit> {
        return runAuthRequest { apiClient.requestPasswordRecovery(request) }
    }

    override suspend fun loginWithProvider(provider: SocialAuthProvider): AuthResult<AuthSession> {
        return runAuthRequest { apiClient.loginWithProvider(provider) }
    }

    private suspend fun <T> runAuthRequest(block: suspend () -> T): AuthResult<T> {
        return try {
            AuthResult.Success(block())
        } catch (error: Throwable) {
            AuthResult.Failure(
                AuthError(
                    message = error.message ?: "Não foi possível concluir a autenticação.",
                    code = null
                )
            )
        }
    }
}
