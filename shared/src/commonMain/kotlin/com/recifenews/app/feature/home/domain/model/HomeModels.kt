package com.recifenews.app.feature.home.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val mainNeighborhood: String,
    val followedNeighborhoods: List<String>
)

enum class FeedTab(val label: String) {
    Todos("Todos"),
    Recentes("Mais recentes"),
    EmAlta("Em alta")
}

data class FeedCategory(
    val id: String,
    val label: String
)

data class ReactionOption(
    val id: String,
    val emoji: String,
    val label: String
)

enum class PostImageKey {
    Ponte,
    Alerta,
    Comunidade,
    Mapa,
    Bairro
}

data class FeedPost(
    val id: Int,
    val author: String,
    val neighborhood: String,
    val timeLabel: String,
    val category: FeedCategory,
    val body: String,
    val imageKey: PostImageKey,
    val reactionCounts: Map<String, Int>,
    val comments: Int,
    val shares: Int,
    val trendScore: Int,
    val pinned: Boolean = false
)

data class HomeFeedSnapshot(
    val user: UserProfile,
    val neighborhoods: List<String>,
    val categories: List<FeedCategory>,
    val reactions: List<ReactionOption>,
    val posts: List<FeedPost>
)

data class CreatePostRequest(
    val author: String,
    val neighborhood: String,
    val categoryId: String,
    val body: String,
    val nextId: Int
)
