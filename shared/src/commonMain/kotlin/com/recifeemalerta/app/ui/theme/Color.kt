package com.recifeemalerta.app.ui.theme

import androidx.compose.ui.graphics.Color

// =========================
// Brand
// =========================

val LightPrimary = Color(0xFF064E93)
val LightOnPrimary = Color(0xFFFFFFFF)

val DarkPrimary = Color(0xFF7AB7FF)
val DarkOnPrimary = Color(0xFF06111F)

// =========================
// Accent / CTA
// =========================

val LightSecondary = Color(0xFFD88A00)
val LightOnSecondary = Color(0xFF16110A)

val DarkSecondary = Color(0xFFFFC857)
val DarkOnSecondary = Color(0xFF1A1305)

// =========================
// Light Theme
// =========================

val LightBackground = Color(0xFFF6F8FC)
val LightOnBackground = Color(0xFF111418)

val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF111418)

val LightSurfaceVariant = Color(0xFFE7EDF5)
val LightOnSurfaceVariant = Color(0xFF4F5B6A)

val LightCard = Color(0xFFFFFFFF)
val LightCardHigh = Color(0xFFFBFCFE)

val LightInput = Color(0xFFFFFFFF)
val LightInputBorder = Color(0xFFCED8E6)

val LightDivider = Color(0xFFDDE5EF)
val LightBorder = Color(0xFFCED8E6)

val LightTextPrimary = Color(0xFF111418)
val LightTextSecondary = Color(0xFF46515F)
val LightTextTertiary = Color(0xFF748094)
val LightTextInverse = Color(0xFFFFFFFF)

val LightBottomBar = Color(0xFFFFFFFF)
val LightBottomBarSelected = Color(0xFF064E93)
val LightBottomBarUnselected = Color(0xFF667386)

// =========================
// Dark Theme
// =========================

val DarkBackground = Color(0xFF0B0D10)
val DarkOnBackground = Color(0xFFF4F7FB)

val DarkSurface = Color(0xFF11161C)
val DarkOnSurface = Color(0xFFF4F7FB)

val DarkSurfaceVariant = Color(0xFF171D25)
val DarkOnSurfaceVariant = Color(0xFFC3CDD8)

val DarkCard = Color(0xFF11161C)
val DarkCardHigh = Color(0xFF1A222C)

val DarkInput = Color(0xFF10151B)
val DarkInputBorder = Color(0xFF27313D)

val DarkDivider = Color(0xFF202934)
val DarkBorder = Color(0xFF27313D)

val DarkTextPrimary = Color(0xFFF4F7FB)
val DarkTextSecondary = Color(0xFFC3CDD8)
val DarkTextTertiary = Color(0xFF8E9AAA)
val DarkTextInverse = Color(0xFF111418)

val DarkBottomBar = Color(0xFF10151B)
val DarkBottomBarSelected = Color(0xFF7AB7FF)
val DarkBottomBarUnselected = Color(0xFF8390A2)

// =========================
// States
// =========================

val Success = Color(0xFF20B86A)
val Warning = Color(0xFFE09A18)
val Error = Color(0xFFE34F4F)
val Info = Color(0xFF22A7D8)

// =========================
// App Alerts
// =========================

val AlertRain = Color(0xFF38BDF8)
val AlertFlood = Color(0xFF4F8DFF)
val AlertSecurity = Color(0xFFFF6464)
val AlertTraffic = Color(0xFFFF9B45)
val AlertEvent = Color(0xFFA78BFA)

val LightScrim = Color(0x99000000)
val DarkScrim = Color(0xCC000000)
val Transparent = Color.Transparent

object AppColors {
    val White = LightOnPrimary
    val Black = LightTextPrimary
    val Transparent = com.recifeemalerta.app.ui.theme.Transparent

    val BrandOnPrimary = LightOnPrimary
    val BrandOnPrimaryDark = DarkOnPrimary
    val BrandPrimaryContainer = LightBottomBar
    val BrandHeaderDark = LightPrimary
    val HeaderIconSelected = LightSecondary

    val Success = com.recifeemalerta.app.ui.theme.Success
    val Warning = com.recifeemalerta.app.ui.theme.Warning
    val Error = com.recifeemalerta.app.ui.theme.Error
    val Info = com.recifeemalerta.app.ui.theme.Info
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
