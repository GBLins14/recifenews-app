package com.recifenews.app.feature.home.data

import com.recifenews.app.core.location.recifeNeighborhoods
import com.recifenews.app.feature.home.domain.model.CreatePostRequest
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.HomeCatalog
import com.recifenews.app.feature.home.domain.model.HomeFeedSnapshot
import com.recifenews.app.feature.home.domain.model.PostImageKey
import com.recifenews.app.feature.home.domain.model.UserProfile
import com.recifenews.app.feature.home.domain.repository.HomeFeedRepository

class InMemoryHomeFeedRepository : HomeFeedRepository {
    override fun loadFeed(): HomeFeedSnapshot {
        val seedPosts = seedPosts()
        val generatedPosts = generatedNeighborhoodPosts(
            existingNeighborhoods = seedPosts.map { it.neighborhood }.toSet()
        )

        return HomeFeedSnapshot(
            user = currentUser,
            neighborhoods = recifeNeighborhoods,
            categories = HomeCatalog.categories,
            reactions = HomeCatalog.reactions,
            posts = seedPosts + generatedPosts
        )
    }

    override fun createPost(request: CreatePostRequest): FeedPost {
        return FeedPost(
            id = request.nextId,
            author = request.author,
            neighborhood = request.neighborhood,
            timeLabel = "agora",
            category = HomeCatalog.category(request.categoryId),
            body = request.body,
            imageKey = PostImageKey.Ponte,
            reactionCounts = emptyMap(),
            comments = 0,
            shares = 0,
            trendScore = 100
        )
    }

    private val currentUser = UserProfile(
        id = "user-joao-silva",
        name = "João Silva",
        mainNeighborhood = "Afogados",
        followedNeighborhoods = listOf("Boa Vista", "Derby", "Casa Amarela", "Boa Viagem")
    )

    private fun seedPosts(): List<FeedPost> {
        return listOf(
            FeedPost(
                id = 8,
                author = "Maria",
                neighborhood = "Afogados",
                timeLabel = "8 min",
                category = HomeCatalog.category("alerts"),
                body = "Rua alagada perto da praça, trânsito lento no local.",
                imageKey = PostImageKey.Mapa,
                reactionCounts = mapOf("like" to 12),
                comments = 5,
                shares = 3,
                trendScore = 96,
                pinned = true
            ),
            FeedPost(
                id = 7,
                author = "João",
                neighborhood = "Afogados",
                timeLabel = "14 min",
                category = HomeCatalog.category("traffic"),
                body = "Poste apagado na esquina desde cedo.",
                imageKey = PostImageKey.Alerta,
                reactionCounts = mapOf("like" to 8),
                comments = 3,
                shares = 2,
                trendScore = 88
            ),
            FeedPost(
                id = 6,
                author = "Ana",
                neighborhood = "Afogados",
                timeLabel = "22 min",
                category = HomeCatalog.category("culture"),
                body = "Feirinha da comunidade começa às 18h hoje.",
                imageKey = PostImageKey.Comunidade,
                reactionCounts = mapOf("like" to 15),
                comments = 6,
                shares = 4,
                trendScore = 82
            ),
            FeedPost(
                id = 5,
                author = "João Silva",
                neighborhood = "Boa Viagem",
                timeLabel = "2 min",
                category = HomeCatalog.category("news"),
                body = "Pôr do sol incrível hoje na Praia de Boa Viagem!",
                imageKey = PostImageKey.Ponte,
                reactionCounts = mapOf("like" to 128, "love" to 34, "wow" to 12),
                comments = 24,
                shares = 12,
                trendScore = 96,
                pinned = true
            ),
            FeedPost(
                id = 4,
                author = "Plantão Recife",
                neighborhood = "Derby",
                timeLabel = "15 min",
                category = HomeCatalog.category("traffic"),
                body = "Trânsito complicado na Av. Agamenon Magalhães sentido Boa Viagem. Evite a faixa da direita.",
                imageKey = PostImageKey.Alerta,
                reactionCounts = mapOf("like" to 82, "angry" to 21, "wow" to 9),
                comments = 19,
                shares = 31,
                trendScore = 88
            ),
            FeedPost(
                id = 3,
                author = "Maria Carvalho",
                neighborhood = "Bairro do Recife",
                timeLabel = "38 min",
                category = HomeCatalog.category("culture"),
                body = "Programação gratuita no Marco Zero começa às 18h com música, feira criativa e gastronomia local.",
                imageKey = PostImageKey.Comunidade,
                reactionCounts = mapOf("like" to 76, "love" to 25, "laugh" to 8),
                comments = 11,
                shares = 18,
                trendScore = 74
            ),
            FeedPost(
                id = 2,
                author = "Defesa Civil",
                neighborhood = "Casa Amarela",
                timeLabel = "1 h",
                category = HomeCatalog.category("alerts"),
                body = "Chuva moderada prevista para a tarde. Acompanhe pontos de alagamento e rotas seguras no mapa.",
                imageKey = PostImageKey.Mapa,
                reactionCounts = mapOf("like" to 64, "wow" to 18, "sad" to 6),
                comments = 8,
                shares = 26,
                trendScore = 80
            ),
            FeedPost(
                id = 1,
                author = "Ana Souza",
                neighborhood = "Várzea",
                timeLabel = "2 h",
                category = HomeCatalog.category("community"),
                body = "Mutirão de limpeza na praça começa amanhã cedo. Quem puder levar luvas e sacos reforçados ajuda bastante.",
                imageKey = PostImageKey.Bairro,
                reactionCounts = mapOf("like" to 41, "love" to 19),
                comments = 15,
                shares = 9,
                trendScore = 52
            )
        )
    }

    private fun generatedNeighborhoodPosts(existingNeighborhoods: Set<String>): List<FeedPost> {
        return recifeNeighborhoods
            .filterNot { it in existingNeighborhoods }
            .mapIndexed { index, neighborhood ->
                val category = HomeCatalog.categoryForIndex(index)

                FeedPost(
                    id = -1000 - index,
                    author = authorForIndex(index),
                    neighborhood = neighborhood,
                    timeLabel = "${(index % 8) + 1} h",
                    category = category,
                    body = bodyForNeighborhood(neighborhood, category.id),
                    imageKey = imageForIndex(index),
                    reactionCounts = reactionCountsForIndex(index),
                    comments = (index % 17) + 2,
                    shares = (index % 11) + 1,
                    trendScore = 30 + (index % 65)
                )
            }
    }

    private fun authorForIndex(index: Int): String {
        return listOf("Plantão Recife", "Maria Carvalho", "Lucas Ferreira", "Ana Souza", "Rede Recife")[index % 5]
    }

    private fun bodyForNeighborhood(neighborhood: String, categoryId: String): String {
        return when (categoryId) {
            "traffic" -> "Movimentação intensa em $neighborhood. Moradores relatam lentidão nos principais acessos."
            "alerts" -> "Alerta local em $neighborhood: acompanhe atualizações da comunidade e rotas alternativas."
            "culture" -> "Programação cultural de $neighborhood reúne música, gastronomia e atividades gratuitas."
            "community" -> "Moradores de $neighborhood organizam uma ação colaborativa para melhorar o bairro."
            else -> "Atualização ao vivo de $neighborhood com os principais acontecimentos das últimas horas."
        }
    }

    private fun imageForIndex(index: Int): PostImageKey {
        return when (index % 5) {
            0 -> PostImageKey.Ponte
            1 -> PostImageKey.Alerta
            2 -> PostImageKey.Comunidade
            3 -> PostImageKey.Mapa
            else -> PostImageKey.Bairro
        }
    }

    private fun reactionCountsForIndex(index: Int): Map<String, Int> {
        return mapOf(
            "like" to (18 + index % 80),
            "love" to (index % 22),
            "laugh" to (index % 9),
            "wow" to (index % 15),
            "sad" to (index % 6),
            "angry" to (index % 5)
        ).filterValues { it > 0 }
    }
}
