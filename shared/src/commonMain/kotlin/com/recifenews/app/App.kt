package com.recifenews.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.recifenews.app.di.AppDependencies
import com.recifenews.app.di.ProvideAppDependencies
import com.recifenews.app.navigation.Screen
import com.recifenews.app.ui.screens.home.HomeScreen
import com.recifenews.app.ui.screens.login.LoginScreen
import com.recifenews.app.ui.screens.neighborhood.FollowNeighborhoodsScreen
import com.recifenews.app.ui.screens.neighborhood.NeighborhoodSelectionScreen
import com.recifenews.app.ui.screens.onboarding.OnboardingScreen
import com.recifenews.app.ui.screens.recovery.RecoveryScreen
import com.recifenews.app.ui.screens.register.RegisterScreen
import com.recifenews.app.ui.theme.AppTheme
import com.recifenews.app.ui.theme.ProvideThemeController

@Composable
@Preview
fun App() {
    val backStack = remember { mutableStateListOf<Screen>(Screen.Onboarding) }
    val dependencies = remember { AppDependencies() }
    val systemDarkTheme = isSystemInDarkTheme()
    var darkThemeOverride by remember { mutableStateOf<Boolean?>(null) }
    val darkTheme = darkThemeOverride ?: systemDarkTheme
    val currentScreen = backStack.last()

    fun navigate(screen: Screen) {
        backStack.add(screen)
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    AppTheme(darkTheme = darkTheme) {
        ProvideThemeController(
            isDarkTheme = darkTheme,
            onToggleDarkTheme = { darkThemeOverride = !darkTheme }
        ) {
            ProvideAppDependencies(dependencies = dependencies) {
                when (val screen = currentScreen) {
                    Screen.Onboarding -> {
                        OnboardingScreen(
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }

                    Screen.Home -> {
                        HomeScreen(
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }

                    Screen.Login -> {
                        LoginScreen(
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }

                    Screen.Register -> {
                        RegisterScreen(
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }

                    Screen.Recovery -> {
                        RecoveryScreen(
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }

                    Screen.NeighborhoodSelection -> {
                        NeighborhoodSelectionScreen(
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }

                    is Screen.FollowNeighborhoods -> {
                        FollowNeighborhoodsScreen(
                            mainNeighborhood = screen.mainNeighborhood,
                            onNavigate = ::navigate,
                            onBack = ::navigateBack
                        )
                    }
                }
            }
        }
    }
}
