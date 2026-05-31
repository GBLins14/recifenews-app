package com.recifenews.app.ui.theme

import androidx.compose.ui.graphics.Color

val BrandPrimary = Color(0xFF0042CE)
val BrandPrimaryLight = Color(0xFF4D88FF)

val BrandSecondary = Color(0xFFFDB913)
val BrandSecondaryLight = Color(0xFFFFD152)

val LightBackground = Color(0xFFF9FAFB)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF1F3F4)

val LightTextPrimary = Color(0xFF111827)
val LightTextSecondary = Color(0xFF4B5563)
val LightTextHint = Color(0xFF9CA3AF)

val LightOutline = Color(0xFFE5E7EB)

val DarkBackground = Color(0xFF0F172A)
val DarkSurface = Color(0xFF1E293B)
val DarkSurfaceVariant = Color(0xFF334155)

val DarkTextPrimary = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFFCBD5E1)

val DarkOutline = Color(0xFF334155)

object AppColors {
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val Transparent = Color.Transparent

    val BrandOnPrimary = White
    val BrandOnPrimaryDark = Color(0xFF061A3A)
    val BrandPrimaryContainer = Color(0xFFDCE7FF)
    val BrandHeaderDark = Color(0xFF001F54)
    val HeaderIconSelected = BrandSecondaryLight

    val Success = Color(0xFF10B981)
    val Warning = Color(0xFFF59E0B)
    val Error = Color(0xFFEF4444)
    val Info = Color(0xFF3B82F6)
    val ErrorContainer = Color(0xFFFFB4AB)
    val OnErrorContainer = Color(0xFF690005)

    val TextPrimary = LightTextPrimary
    val TextSecondary = Color(0xFF6B7280)
    val TextMuted = LightTextHint
    val ButtonTextDark = Color(0xFF1F2937)

    val FeedBackground = Color(0xFFF2F5FA)
    val CardBorder = Color(0xFFE8EDF5)
    val DisabledContainer = Color(0xFFE5E7EB)
    val CategoryCommunity = Color(0xFF7C3AED)

    val ReactionLike = Color(0xFF2563EB)
    val ReactionLove = Color(0xFFE11D48)
    val ReactionLaugh = Warning
    val ReactionWow = CategoryCommunity
    val ReactionSad = Color(0xFF0EA5E9)
    val ReactionAngry = Error

    val GlassWhite = White.copy(alpha = 0.1f)
    val GlassBlack = Black.copy(alpha = 0.1f)

    val ButtonYellow = Color(0xFFFDB913)
    val ButtonBlue = Color(0xFF00369B)
}
