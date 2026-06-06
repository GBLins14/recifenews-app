package com.recifeemalerta.app.feature.onboarding.domain.repository

import com.recifeemalerta.app.feature.onboarding.domain.model.OnboardingPage

interface OnboardingRepository {
    fun pages(): List<OnboardingPage>
}
