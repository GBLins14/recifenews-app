package com.recifenews.app.feature.onboarding.data

import com.recifenews.app.feature.home.domain.model.PostImageKey
import com.recifenews.app.feature.onboarding.domain.model.OnboardingPage
import com.recifenews.app.feature.onboarding.domain.repository.OnboardingRepository

class InMemoryOnboardingRepository : OnboardingRepository {
    override fun pages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(
                id = "city-live",
                title = "Tudo o que acontece no Recife.",
                description = "Acompanhe notícias, alertas, eventos e informações importantes da cidade em tempo real.",
                imageKey = PostImageKey.Ponte
            ),
            OnboardingPage(
                id = "mobile-city",
                title = "Recife na palma da sua mão.",
                description = "Acompanhe tudo o que acontece na cidade diretamente do seu celular, onde você estiver.",
                imageKey = PostImageKey.Bairro
            ),
            OnboardingPage(
                id = "alerts",
                title = "Receba alertas importantes.",
                description = "Fique por dentro de alagamentos, chuvas, trânsito, acidentes e ocorrências que podem impactar seu dia.",
                imageKey = PostImageKey.Alerta
            ),
            OnboardingPage(
                id = "community",
                title = "Uma cidade construída por todos.",
                description = "Compartilhe informações, ajude outras pessoas e fortaleça a rede de colaboração do Recife.",
                imageKey = PostImageKey.Comunidade
            ),
            OnboardingPage(
                id = "explore",
                title = "Explore e descubra Recife.",
                description = "Encontre lugares, eventos, gastronomia, lazer e tudo o que a cidade tem a oferecer.",
                imageKey = PostImageKey.Mapa
            )
        )
    }
}
