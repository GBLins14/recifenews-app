package com.recifenews.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable data object Home : Screen
    @Serializable data object Login : Screen
    @Serializable data object Onboarding : Screen
    @Serializable data object Register : Screen
    @Serializable data object Recovery : Screen
    @Serializable data object NeighborhoodSelection : Screen
    @Serializable data class FollowNeighborhoods(val mainNeighborhood: String) : Screen
}
