package com.recifeemalerta.app.feature.onboarding.domain.model

import com.recifeemalerta.app.feature.home.domain.model.PostImageKey

data class OnboardingPage(
    val id: String,
    val title: String,
    val description: String,
    val imageKey: PostImageKey
)
