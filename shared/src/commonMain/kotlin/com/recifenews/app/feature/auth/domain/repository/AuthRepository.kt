package com.recifenews.app.feature.auth.domain.repository

import com.recifenews.app.feature.auth.domain.model.AuthResult
import com.recifenews.app.feature.auth.domain.model.AuthSession
import com.recifenews.app.feature.auth.domain.model.LoginRequest
import com.recifenews.app.feature.auth.domain.model.RecoveryRequest
import com.recifenews.app.feature.auth.domain.model.RegisterRequest
import com.recifenews.app.feature.auth.domain.model.SocialAuthProvider

interface AuthRepository {
    suspend fun login(request: LoginRequest): AuthResult<AuthSession>
    suspend fun register(request: RegisterRequest): AuthResult<AuthSession>
    suspend fun requestPasswordRecovery(request: RecoveryRequest): AuthResult<Unit>
    suspend fun loginWithProvider(provider: SocialAuthProvider): AuthResult<AuthSession>
}
