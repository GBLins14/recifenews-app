package com.recifenews.app.feature.onboarding.domain.model

import com.recifenews.app.feature.home.domain.model.PostImageKey

data class OnboardingPage(
    val id: String,
    val title: String,
    val description: String,
    val imageKey: PostImageKey
)
