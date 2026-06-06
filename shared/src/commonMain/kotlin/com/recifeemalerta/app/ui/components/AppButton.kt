package com.recifeemalerta.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.ui.theme.AppColors

enum class AppButtonStyle {
    Yellow, Blue, Ghost, YellowOutlined
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppButtonStyle = AppButtonStyle.Yellow,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.97f else 1f)

    val containerColor = when (style) {
        AppButtonStyle.Yellow -> AppColors.ButtonYellow
        AppButtonStyle.Blue -> AppColors.ButtonBlue
        AppButtonStyle.Ghost, AppButtonStyle.YellowOutlined -> AppColors.Transparent
    }

    val contentColor = when (style) {
        AppButtonStyle.Yellow -> AppColors.White
        AppButtonStyle.Blue -> AppColors.White
        AppButtonStyle.Ghost -> MaterialTheme.colorScheme.primary
        AppButtonStyle.YellowOutlined -> AppColors.ButtonYellow
    }

    val border = if (style == AppButtonStyle.YellowOutlined) {
        BorderStroke(1.5.dp, AppColors.ButtonYellow)
    } else null

    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .scale(scale),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = if (style == AppButtonStyle.YellowOutlined) AppColors.Transparent else MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.55f)
        ),
        border = border,
        shape = RoundedCornerShape(14.dp),
        elevation = if (style == AppButtonStyle.Ghost || style == AppButtonStyle.YellowOutlined) null else ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}
