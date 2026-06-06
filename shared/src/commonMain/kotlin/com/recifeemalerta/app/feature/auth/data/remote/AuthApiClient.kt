package com.recifeemalerta.app.feature.auth.data.remote

import com.recifeemalerta.app.feature.auth.domain.model.AuthSession
import com.recifeemalerta.app.feature.auth.domain.model.LoginRequest
import com.recifeemalerta.app.feature.auth.domain.model.RecoveryRequest
import com.recifeemalerta.app.feature.auth.domain.model.RegisterRequest
import com.recifeemalerta.app.feature.auth.domain.model.SocialAuthProvider

interface AuthApiClient {
    suspend fun login(request: LoginRequest): AuthSession
    suspend fun register(request: RegisterRequest): AuthSession
    suspend fun requestPasswordRecovery(request: RecoveryRequest)
    suspend fun loginWithProvider(provider: SocialAuthProvider): AuthSession
}
