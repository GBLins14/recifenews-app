package com.recifenews.app.ui.theme

import androidx.compose.ui.graphics.Color

// =========================
// Brand
// =========================

val LightPrimary = Color(0xFF0754C8)
val LightOnPrimary = Color(0xFFFFFFFF)

val DarkPrimary = Color(0xFF4D8DFF)
val DarkOnPrimary = Color(0xFF061A3A)

// =========================
// Accent / CTA
// =========================

val LightSecondary = Color(0xFFF6B51E)
val LightOnSecondary = Color(0xFF1F2937)

val DarkSecondary = Color(0xFFFFC83D)
val DarkOnSecondary = Color(0xFF1F2937)

// =========================
// Light Theme
// =========================

val LightBackground = Color(0xFFF6F8FC)
val LightOnBackground = Color(0xFF0F172A)

val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF0F172A)

val LightSurfaceVariant = Color(0xFFEFF4FA)
val LightOnSurfaceVariant = Color(0xFF5B677A)

val LightCard = Color(0xFFFFFFFF)
val LightCardHigh = Color(0xFFF9FBFE)

val LightInput = Color(0xFFFFFFFF)
val LightInputBorder = Color(0xFFD8E1EE)

val LightDivider = Color(0xFFE1E8F2)
val LightBorder = Color(0xFFD8E1EE)

val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF526071)
val LightTextTertiary = Color(0xFF8A97A8)
val LightTextInverse = Color(0xFFFFFFFF)

val LightBottomBar = Color(0xFFFFFFFF)
val LightBottomBarSelected = Color(0xFF0754C8)
val LightBottomBarUnselected = Color(0xFF64748B)

// =========================
// Dark Theme
// =========================

val DarkBackground = Color(0xFF07111F)
val DarkOnBackground = Color(0xFFF8FAFC)

val DarkSurface = Color(0xFF101B2D)
val DarkOnSurface = Color(0xFFF8FAFC)

val DarkSurfaceVariant = Color(0xFF18263A)
val DarkOnSurfaceVariant = Color(0xFFCBD5E1)

val DarkCard = Color(0xFF101B2D)
val DarkCardHigh = Color(0xFF18263A)

val DarkInput = Color(0xFF101B2D)
val DarkInputBorder = Color(0xFF33445F)

val DarkDivider = Color(0xFF26364E)
val DarkBorder = Color(0xFF33445F)

val DarkTextPrimary = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFFCBD5E1)
val DarkTextTertiary = Color(0xFF94A3B8)
val DarkTextInverse = Color(0xFF0F172A)

val DarkBottomBar = Color(0xFF101B2D)
val DarkBottomBarSelected = Color(0xFF66A3FF)
val DarkBottomBarUnselected = Color(0xFFAAB7C8)

// =========================
// States
// =========================

val Success = Color(0xFF16A34A)
val Warning = Color(0xFFF59E0B)
val Error = Color(0xFFDC2626)
val Info = Color(0xFF0284C7)

// =========================
// App Alerts
// =========================

val AlertRain = Color(0xFF0284C7)
val AlertFlood = Color(0xFF2563EB)
val AlertSecurity = Color(0xFFDC2626)
val AlertTraffic = Color(0xFFF97316)
val AlertEvent = Color(0xFF7C3AED)

val LightScrim = Color(0x99000000)
val DarkScrim = Color(0xCC000000)
val Transparent = Color.Transparent

object AppColors {
    val White = LightOnPrimary
    val Black = LightTextPrimary
    val Transparent = com.recifenews.app.ui.theme.Transparent

    val BrandOnPrimary = LightOnPrimary
    val BrandOnPrimaryDark = DarkOnPrimary
    val BrandPrimaryContainer = LightBottomBar
    val BrandHeaderDark = LightPrimary
    val HeaderIconSelected = LightSecondary

    val Success = com.recifenews.app.ui.theme.Success
    val Warning = com.recifenews.app.ui.theme.Warning
    val Error = com.recifenews.app.ui.theme.Error
    val Info = com.recifenews.app.ui.theme.Info
    val ErrorContainer = Error
    val OnErrorContainer = LightOnPrimary

    val TextPrimary = LightTextPrimary
    val TextSecondary = LightTextSecondary
    val TextMuted = LightTextTertiary
    val ButtonTextDark = LightOnSecondary

    val FeedBackground = LightBackground
    val CardBorder = LightBorder
    val DisabledContainer = LightSurfaceVariant
    val CategoryCommunity = AlertEvent

    val ReactionLike = AlertFlood
    val ReactionLove = AlertSecurity
    val ReactionLaugh = Warning
    val ReactionWow = AlertEvent
    val ReactionSad = AlertRain
    val ReactionAngry = Error

    val GlassWhite = LightOnPrimary.copy(alpha = 0.1f)
    val GlassBlack = LightTextPrimary.copy(alpha = 0.1f)

    val ButtonYellow = LightSecondary
    val ButtonBlue = LightPrimary
}
