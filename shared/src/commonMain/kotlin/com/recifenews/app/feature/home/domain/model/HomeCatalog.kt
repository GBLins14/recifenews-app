package com.recifenews.app.feature.home.domain.model

object HomeCatalog {
    val categories = listOf(
        FeedCategory(id = "news", label = "Notícias"),
        FeedCategory(id = "traffic", label = "Trânsito"),
        FeedCategory(id = "alerts", label = "Alertas"),
        FeedCategory(id = "culture", label = "Cultura"),
        FeedCategory(id = "community", label = "Comunidade")
    )

    val reactions = listOf(
        ReactionOption(id = "like", emoji = "👍", label = "Curtir"),
        ReactionOption(id = "love", emoji = "❤️", label = "Amei"),
        ReactionOption(id = "laugh", emoji = "😂", label = "Ri"),
        ReactionOption(id = "wow", emoji = "😮", label = "Uau"),
        ReactionOption(id = "sad", emoji = "😢", label = "Triste"),
        ReactionOption(id = "angry", emoji = "😡", label = "Indignado")
    )

    fun category(id: String): FeedCategory {
        return categories.firstOrNull { it.id == id } ?: categories.first()
    }

    fun categoryForIndex(index: Int): FeedCategory {
        return categories[index % categories.size]
    }

    fun reaction(id: String?): ReactionOption? {
        return reactions.firstOrNull { it.id == id }
    }
}
