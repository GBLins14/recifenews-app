package com.recifenews.app.ui.screens.home.components

import com.recifenews.app.ui.icons.AppIcons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recifenews.app.feature.home.ui.state.HomeNavigationItem
import com.recifenews.app.ui.screens.home.homePalette

@Composable
internal fun HomeBottomNavigation(
    selectedItem: HomeNavigationItem,
    onItemSelected: (HomeNavigationItem) -> Unit
) {
    val colors = homePalette()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = colors.card,
        border = BorderStroke(1.dp, colors.bottomBarBorder),
        tonalElevation = 0.dp,
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavAction(
                    label = "Início",
                    icon = AppIcons.Home,
                    selected = selectedItem == HomeNavigationItem.Home,
                    onClick = { onItemSelected(HomeNavigationItem.Home) }
                )
                BottomNavAction(
                    label = "Mapa",
                    icon = AppIcons.LocationOn,
                    selected = selectedItem == HomeNavigationItem.Neighborhoods,
                    onClick = { onItemSelected(HomeNavigationItem.Neighborhoods) }
                )
                CreatePostAction(
                    onClick = { onItemSelected(HomeNavigationItem.NewPost) }
                )
                BottomNavAction(
                    label = "Alertas",
                    icon = AppIcons.Notifications,
                    selected = selectedItem == HomeNavigationItem.Alerts,
                    onClick = { onItemSelected(HomeNavigationItem.Alerts) }
                )
                BottomNavAction(
                    label = "Perfil",
                    icon = AppIcons.Person,
                    selected = selectedItem == HomeNavigationItem.Profile,
                    onClick = { onItemSelected(HomeNavigationItem.Profile) }
                )
            }
        }
    }
}

@Composable
private fun RowScope.CreatePostAction(
    onClick: () -> Unit
) {
    val colors = homePalette()

    Box(
        modifier = Modifier
            .weight(1f)
            .height(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .size(43.dp)
                .clip(CircleShape)
                .clickable(onClick = onClick),
            shape = CircleShape,
            color = colors.accentStrong,
            contentColor = colors.onAccent,
            tonalElevation = 0.dp,
            shadowElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = AppIcons.Add,
                    contentDescription = "Criar post",
                    tint = colors.onAccent,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavAction(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = homePalette()
    val contentColor = if (selected) colors.bottomItemSelected else colors.bottomItemUnselected

    Column(
        modifier = Modifier
            .weight(1f)
            .height(47.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 2.dp, vertical = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(19.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold),
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
