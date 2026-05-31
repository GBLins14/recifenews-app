package com.recifenews.app.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.ui.components.AppFeedback
import com.recifenews.app.ui.screens.home.CardBorder
import com.recifenews.app.ui.screens.home.HomeBlue
import com.recifenews.app.ui.screens.home.TextPrimary
import com.recifenews.app.ui.screens.home.TextSecondary
import com.recifenews.app.ui.screens.home.categoryColor
import com.recifenews.app.ui.theme.AppColors

@Composable
internal fun FeedFeedback(
    message: String,
    onClose: () -> Unit
) {
    AppFeedback(message = message, onClose = onClose, color = HomeBlue)
}

@Composable
internal fun PinnedPostsCard(posts: List<FeedPost>) {
    if (posts.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        border = BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Posts fixados",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Ver todos",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = HomeBlue
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            posts.forEachIndexed { index, post ->
                PinnedPostRow(post = post)
                if (index != posts.lastIndex) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = CardBorder)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
internal fun SectionTitle(
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                color = TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(AppColors.White)
                .border(BorderStroke(1.dp, CardBorder), RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "#ChuvaRecife",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = HomeBlue
            )
        }
    }
}

@Composable
internal fun EmptyFeedState(searchQuery: String, neighborhood: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(AppColors.White)
            .border(BorderStroke(1.dp, CardBorder), RoundedCornerShape(18.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nada encontrado em $neighborhood",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (searchQuery.isBlank()) "Novos posts aparecerão aqui." else "Tente buscar por outro termo.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}

@Composable
private fun PinnedPostRow(post: FeedPost) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(categoryColor(post.category.id).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = post.category.label.take(1),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                color = categoryColor(post.category.id)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = post.author,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = post.timeLabel,
            style = MaterialTheme.typography.bodySmall,
            color = AppColors.TextMuted
        )
    }
}
