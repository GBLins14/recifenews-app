package com.recifenews.app.ui.screens.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.recifenews.app.di.LocalAppDependencies
import com.recifenews.app.feature.onboarding.ui.state.OnboardingStateHolder
import com.recifenews.app.navigation.Screen
import com.recifenews.app.ui.screens.onboarding.components.OnboardingFooter
import com.recifenews.app.ui.screens.onboarding.components.OnboardingHeader
import com.recifenews.app.ui.screens.onboarding.components.OnboardingPageContent
import com.recifenews.app.ui.preview.AppPreview
import com.recifenews.app.ui.theme.AppColors
import com.recifenews.app.ui.theme.LocalThemeController
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.onboardingRepository) {
        OnboardingStateHolder(dependencies.onboardingRepository)
    }
    val state = stateHolder.state
    val pagerState = rememberPagerState(pageCount = { state.pages.size })
    val coroutineScope = rememberCoroutineScope()
    val themeController = LocalThemeController.current

    val isFirstPage = pagerState.currentPage == 0
    val backgroundColor by animateColorAsState(
        targetValue = if (isFirstPage && !themeController.isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        animationSpec = spring(stiffness = 500f)
    )
    val contentColor = if (isFirstPage) AppColors.White else MaterialTheme.colorScheme.onBackground
    val secondaryContentColor = contentColor.copy(alpha = 0.7f)

    Surface(
        color = backgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            OnboardingHeader(
                showBack = pagerState.currentPage > 0,
                showSkip = pagerState.currentPage < state.lastPageIndex,
                contentColor = contentColor,
                onBack = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage - 1,
                            animationSpec = tween(400)
                        )
                    }
                },
                onSkip = { onNavigate(Screen.Login) }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                beyondViewportPageCount = 1,
                key = { state.pages[it].id }
            ) { pageIndex ->
                OnboardingPageContent(
                    page = state.pages[pageIndex],
                    contentColor = contentColor,
                    secondaryColor = secondaryContentColor
                )
            }

            OnboardingFooter(
                totalPages = state.pages.size,
                currentPage = pagerState.currentPage,
                isLastPage = pagerState.currentPage == state.lastPageIndex,
                onNext = {
                    if (pagerState.currentPage < state.lastPageIndex) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(400)
                            )
                        }
                    } else {
                        onNavigate(Screen.Login)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    AppPreview {
        OnboardingScreen(
            onNavigate = {},
            onBack = {}
        )
    }
}
