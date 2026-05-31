package com.recifenews.app.feature.onboarding.domain.repository

import com.recifenews.app.feature.onboarding.domain.model.OnboardingPage

interface OnboardingRepository {
    fun pages(): List<OnboardingPage>
}
