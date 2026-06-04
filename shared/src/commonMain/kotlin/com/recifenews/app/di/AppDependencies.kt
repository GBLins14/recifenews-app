package com.recifenews.app.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.recifenews.app.feature.auth.data.InMemoryAuthRepository
import com.recifenews.app.feature.auth.domain.repository.AuthRepository
import com.recifenews.app.feature.home.data.InMemoryHomeFeedRepository
import com.recifenews.app.feature.home.domain.repository.HomeFeedRepository
import com.recifenews.app.feature.map.data.InMemoryLiveMapRepository
import com.recifenews.app.feature.map.domain.repository.LiveMapRepository
import com.recifenews.app.feature.neighborhood.data.InMemoryNeighborhoodRepository
import com.recifenews.app.feature.neighborhood.domain.repository.NeighborhoodRepository
import com.recifenews.app.feature.onboarding.data.InMemoryOnboardingRepository
import com.recifenews.app.feature.onboarding.domain.repository.OnboardingRepository

class AppDependencies(
    val authRepository: AuthRepository = InMemoryAuthRepository(),
    val homeFeedRepository: HomeFeedRepository = InMemoryHomeFeedRepository(),
    val liveMapRepository: LiveMapRepository = InMemoryLiveMapRepository(),
    val neighborhoodRepository: NeighborhoodRepository = InMemoryNeighborhoodRepository(),
    val onboardingRepository: OnboardingRepository = InMemoryOnboardingRepository()
)

val LocalAppDependencies = staticCompositionLocalOf<AppDependencies> {
    error("AppDependencies not provided")
}

@Composable
fun ProvideAppDependencies(
    dependencies: AppDependencies,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppDependencies provides dependencies) {
        content()
    }
}
