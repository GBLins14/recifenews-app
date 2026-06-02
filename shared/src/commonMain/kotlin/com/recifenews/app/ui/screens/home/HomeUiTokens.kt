package com.recifenews.app.ui.screens.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.PostImageKey
import com.recifenews.app.feature.home.ui.state.PostInteraction
import com.recifenews.app.ui.theme.AppColors
import com.recifenews.app.ui.theme.DarkBackground
import com.recifenews.app.ui.theme.DarkBorder
import com.recifenews.app.ui.theme.DarkBottomBar
import com.recifenews.app.ui.theme.DarkBottomBarSelected
import com.recifenews.app.ui.theme.DarkBottomBarUnselected
import com.recifenews.app.ui.theme.DarkCard
import com.recifenews.app.ui.theme.DarkCardHigh
import com.recifenews.app.ui.theme.DarkPrimary
import com.recifenews.app.ui.theme.DarkTextPrimary
import com.recifenews.app.ui.theme.DarkTextSecondary
import com.recifenews.app.ui.theme.DarkTextTertiary
import com.recifenews.app.ui.theme.LightBackground
import com.recifenews.app.ui.theme.LightBorder
import com.recifenews.app.ui.theme.LightBottomBar
import com.recifenews.app.ui.theme.LightBottomBarSelected
import com.recifenews.app.ui.theme.LightBottomBarUnselected
import com.recifenews.app.ui.theme.LightCard
import com.recifenews.app.ui.theme.LightCardHigh
import com.recifenews.app.ui.theme.LightPrimary
import com.recifenews.app.ui.theme.LightTextPrimary
import com.recifenews.app.ui.theme.LightTextSecondary
import com.recifenews.app.ui.theme.LightTextTertiary
import org.jetbrains.compose.resources.DrawableResource
import recifenews.shared.generated.resources.Res
import recifenews.shared.generated.resources.onboarding_alerta
import recifenews.shared.generated.resources.onboarding_bairro
import recifenews.shared.generated.resources.onboarding_comunidade
import recifenews.shared.generated.resources.onboarding_mapa
import recifenews.shared.generated.resources.onboarding_ponte

internal val HomeBlue = AppColors.ButtonBlue
internal val HomeBlueDark = AppColors.BrandHeaderDark

internal data class HomePalette(
    val accent: Color,
    val accentStrong: Color,
    val headerStart: Color,
    val headerEnd: Color,
    val feedBackground: Color,
    val card: Color,
    val cardBorder: Color,
    val subtleSurface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val onAccent: Color,
    val bottomBar: Color,
    val bottomBarBorder: Color,
    val bottomItemSelected: Color,
    val bottomItemUnselected: Color
)

@Composable
internal fun homePalette(): HomePalette {
    val scheme = MaterialTheme.colorScheme
    val isDark = scheme.background == DarkBackground

    return HomePalette(
        accent = scheme.primary,
        accentStrong = if (isDark) DarkPrimary else LightPrimary,
        headerStart = if (isDark) DarkPrimary else LightPrimary,
        headerEnd = if (isDark) DarkPrimary else LightPrimary,
        feedBackground = if (isDark) DarkBackground else LightBackground,
        card = if (isDark) DarkCard else LightCard,
        cardBorder = if (isDark) DarkBorder else LightBorder,
        subtleSurface = if (isDark) DarkCardHigh else LightCardHigh,
        textPrimary = if (isDark) DarkTextPrimary else LightTextPrimary,
        textSecondary = if (isDark) DarkTextSecondary else LightTextSecondary,
        textMuted = if (isDark) DarkTextTertiary else LightTextTertiary,
        onAccent = AppColors.White,
        bottomBar = if (isDark) DarkBottomBar else LightBottomBar,
        bottomBarBorder = if (isDark) DarkBorder else LightBorder,
        bottomItemSelected = if (isDark) DarkBottomBarSelected else LightBottomBarSelected,
        bottomItemUnselected = if (isDark) DarkBottomBarUnselected else LightBottomBarUnselected
    )
}

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
