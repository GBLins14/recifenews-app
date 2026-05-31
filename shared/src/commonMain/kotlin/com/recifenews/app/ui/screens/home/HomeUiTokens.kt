package com.recifenews.app.ui.screens.home

import androidx.compose.ui.graphics.Color
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.PostImageKey
import com.recifenews.app.feature.home.ui.state.PostInteraction
import com.recifenews.app.ui.theme.AppColors
import org.jetbrains.compose.resources.DrawableResource
import recifenews.shared.generated.resources.Res
import recifenews.shared.generated.resources.onboarding_alerta
import recifenews.shared.generated.resources.onboarding_bairro
import recifenews.shared.generated.resources.onboarding_comunidade
import recifenews.shared.generated.resources.onboarding_mapa
import recifenews.shared.generated.resources.onboarding_ponte

internal val HomeBlue = AppColors.ButtonBlue
internal val HomeBlueDark = AppColors.BrandHeaderDark
internal val FeedBackground = AppColors.FeedBackground
internal val CardBorder = AppColors.CardBorder
internal val TextPrimary = AppColors.TextPrimary
internal val TextSecondary = AppColors.TextSecondary

internal fun initialsFrom(name: String): String {
    val initials = name
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.take(1) }
        .uppercase()

    return initials.ifBlank { "?" }
}

internal fun imageResourceFor(imageKey: PostImageKey): DrawableResource {
    return when (imageKey) {
        PostImageKey.Ponte -> Res.drawable.onboarding_ponte
        PostImageKey.Alerta -> Res.drawable.onboarding_alerta
        PostImageKey.Comunidade -> Res.drawable.onboarding_comunidade
        PostImageKey.Mapa -> Res.drawable.onboarding_mapa
        PostImageKey.Bairro -> Res.drawable.onboarding_bairro
    }
}

internal fun categoryColor(categoryId: String): Color {
    return when (categoryId) {
        "traffic" -> AppColors.Warning
        "alerts" -> AppColors.Info
        "culture" -> AppColors.Success
        "community" -> AppColors.CategoryCommunity
        else -> HomeBlue
    }
}

internal fun reactionColor(reactionId: String): Color {
    return when (reactionId) {
        "like" -> AppColors.ReactionLike
        "love" -> AppColors.ReactionLove
        "laugh" -> AppColors.ReactionLaugh
        "wow" -> AppColors.ReactionWow
        "sad" -> AppColors.ReactionSad
        "angry" -> AppColors.ReactionAngry
        else -> HomeBlue
    }
}

internal fun reactionCountsFor(post: FeedPost, interaction: PostInteraction): Map<String, Int> {
    val reactionId = interaction.reactionId ?: return post.reactionCounts
    val current = post.reactionCounts[reactionId] ?: 0
    return post.reactionCounts + (reactionId to current + 1)
}
