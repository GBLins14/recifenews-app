package com.recifenews.app.ui.screens.onboarding.components

import com.recifenews.app.ui.icons.AppIcons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recifenews.app.feature.onboarding.domain.model.OnboardingPage
import com.recifenews.app.ui.components.AppButton
import com.recifenews.app.ui.components.AppButtonStyle
import com.recifenews.app.ui.components.PaginationDots
import com.recifenews.app.ui.screens.home.imageResourceFor
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun OnboardingHeader(
    showBack: Boolean,
    showSkip: Boolean,
    contentColor: Color,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBack) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Voltar",
                    tint = contentColor
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }

        if (showSkip) {
            TextButton(onClick = onSkip) {
                Text(
                    text = "Pular",
                    color = contentColor,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

@Composable
internal fun OnboardingPageContent(
    page: OnboardingPage,
    contentColor: Color,
    secondaryColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageResourceFor(page.imageKey)),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 34.sp,
                fontSize = 24.sp
            ),
            color = contentColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 26.sp),
            color = secondaryColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun OnboardingFooter(
    totalPages: Int,
    currentPage: Int,
    isLastPage: Boolean,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PaginationDots(
            total = totalPages,
            current = currentPage,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        AppButton(
            text = if (isLastPage) "Começar" else "Próximo",
            style = if (isLastPage) AppButtonStyle.Blue else AppButtonStyle.Yellow,
            onClick = onNext
        )
    }
}
