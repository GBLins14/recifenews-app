package com.recifenews.app.feature.auth.domain.repository

import com.recifenews.app.feature.auth.domain.model.AuthUser
import com.recifenews.app.feature.auth.domain.model.LoginRequest
import com.recifenews.app.feature.auth.domain.model.RecoveryRequest
import com.recifenews.app.feature.auth.domain.model.RegisterRequest
import com.recifenews.app.feature.auth.domain.model.SocialAuthProvider

interface AuthRepository {
    fun login(request: LoginRequest): AuthUser
    fun register(request: RegisterRequest): AuthUser
    fun requestPasswordRecovery(request: RecoveryRequest): Boolean
    fun loginWithProvider(provider: SocialAuthProvider): AuthUser
}
