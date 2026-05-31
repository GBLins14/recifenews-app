package com.recifenews.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val dependencies = remember { AppDependencies() }

    AppTheme {
        ProvideAppDependencies(dependencies = dependencies) {
            NavHost(
                navController = navController,
                startDestination = Screen.Onboarding
            ) {
                composable<Screen.Onboarding> {
                    OnboardingScreen(
                        onNavigate = { screen -> navController.navigate(screen) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Screen.Home> {
                    HomeScreen(
                        onNavigate = { screen -> navController.navigate(screen) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Screen.Login> {
                    LoginScreen(
                        onNavigate = { screen -> navController.navigate(screen) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Screen.Register> {
                    RegisterScreen(
                        onNavigate = { screen -> navController.navigate(screen) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Screen.Recovery> {
                    RecoveryScreen(
                        onNavigate = { screen -> navController.navigate(screen) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Screen.NeighborhoodSelection> {
                    NeighborhoodSelectionScreen(
                        onNavigate = { screen -> navController.navigate(screen) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable<Screen.FollowNeighborhoods> { backStackEntry ->
                    val screen = backStackEntry.toRoute<Screen.FollowNeighborhoods>()
                    FollowNeighborhoodsScreen(
                        mainNeighborhood = screen.mainNeighborhood,
                        onNavigate = { destination -> navController.navigate(destination) },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
