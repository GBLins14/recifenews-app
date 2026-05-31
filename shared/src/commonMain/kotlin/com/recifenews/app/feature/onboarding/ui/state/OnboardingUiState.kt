package com.recifenews.app.feature.onboarding.ui.state

import com.recifenews.app.feature.onboarding.domain.model.OnboardingPage
import com.recifenews.app.feature.onboarding.domain.repository.OnboardingRepository

data class OnboardingUiState(
    val pages: List<OnboardingPage>
) {
    val lastPageIndex: Int
        get() = pages.lastIndex
}

class OnboardingStateHolder(
    repository: OnboardingRepository
) {
    val state = OnboardingUiState(pages = repository.pages())
}
