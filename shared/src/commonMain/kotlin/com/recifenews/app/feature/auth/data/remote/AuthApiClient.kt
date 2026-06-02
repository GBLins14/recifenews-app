package com.recifenews.app.feature.auth.data.remote

import com.recifenews.app.feature.auth.domain.model.AuthSession
import com.recifenews.app.feature.auth.domain.model.LoginRequest
import com.recifenews.app.feature.auth.domain.model.RecoveryRequest
import com.recifenews.app.feature.auth.domain.model.RegisterRequest
import com.recifenews.app.feature.auth.domain.model.SocialAuthProvider

interface AuthApiClient {
    suspend fun login(request: LoginRequest): AuthSession
    suspend fun register(request: RegisterRequest): AuthSession
    suspend fun requestPasswordRecovery(request: RecoveryRequest)
    suspend fun loginWithProvider(provider: SocialAuthProvider): AuthSession
}
