package com.recifenews.app.ui.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recifenews.app.feature.home.domain.model.UserProfile
import com.recifenews.app.ui.screens.home.HomeBlue
import com.recifenews.app.ui.screens.home.HomeBlueDark
import com.recifenews.app.ui.screens.home.initialsFrom
import com.recifenews.app.ui.theme.AppColors

@Composable
internal fun HomeHeader(
    user: UserProfile,
    selectedNeighborhood: String,
    showSearch: Boolean,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSearchToggle: () -> Unit,
    onNeighborhoodClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HomeBlueDark, HomeBlue)
                )
            )
            .safeContentPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserHeaderAvatar(userName = user.name)

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable(onClick = onNeighborhoodClick)
                    .padding(horizontal = 8.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = AppColors.White.copy(alpha = 0.86f),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = selectedNeighborhood,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = AppColors.White.copy(alpha = 0.86f),
                    modifier = Modifier.size(18.dp)
                )
            }

            HeaderIconButton(
                icon = Icons.Default.Search,
                description = "Buscar",
                selected = showSearch,
                onClick = onSearchToggle
            )
            HeaderIconButton(icon = Icons.Default.Notifications, description = "Notificações")
            HeaderIconButton(icon = Icons.Default.Settings, description = "Configurações")
        }

        AnimatedVisibility(visible = showSearch) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Buscar post, bairro ou hashtag",
                            color = AppColors.White.copy(alpha = 0.66f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = AppColors.White.copy(alpha = 0.75f)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AppColors.White,
                        unfocusedTextColor = AppColors.White,
                        cursorColor = AppColors.White,
                        focusedBorderColor = AppColors.White.copy(alpha = 0.28f),
                        unfocusedBorderColor = AppColors.White.copy(alpha = 0.14f),
                        focusedContainerColor = AppColors.White.copy(alpha = 0.12f),
                        unfocusedContainerColor = AppColors.White.copy(alpha = 0.12f)
                    )
                )
            }
        }
    }
}

@Composable
private fun UserHeaderAvatar(userName: String) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(AppColors.White.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initialsFrom(userName),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black),
            color = AppColors.White
        )
    }
}

@Composable
private fun HeaderIconButton(
    icon: ImageVector,
    description: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = if (selected) AppColors.HeaderIconSelected else AppColors.White,
            modifier = Modifier.size(21.dp)
        )
    }
}
