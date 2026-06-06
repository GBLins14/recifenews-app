package com.recifeemalerta.app.feature.auth.domain.repository

import com.recifeemalerta.app.feature.auth.domain.model.AuthResult
import com.recifeemalerta.app.feature.auth.domain.model.AuthSession
import com.recifeemalerta.app.feature.auth.domain.model.LoginRequest
import com.recifeemalerta.app.feature.auth.domain.model.RecoveryRequest
import com.recifeemalerta.app.feature.auth.domain.model.RegisterRequest
import com.recifeemalerta.app.feature.auth.domain.model.SocialAuthProvider

interface AuthRepository {
    suspend fun login(request: LoginRequest): AuthResult<AuthSession>
    suspend fun register(request: RegisterRequest): AuthResult<AuthSession>
    suspend fun requestPasswordRecovery(request: RecoveryRequest): AuthResult<Unit>
    suspend fun loginWithProvider(provider: SocialAuthProvider): AuthResult<AuthSession>
}
