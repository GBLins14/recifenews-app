package com.recifenews.app.ui.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.ReactionOption
import com.recifenews.app.feature.home.domain.model.UserProfile
import com.recifenews.app.feature.home.ui.state.PostInteraction
import com.recifenews.app.ui.screens.home.CardBorder
import com.recifenews.app.ui.screens.home.FeedBackground
import com.recifenews.app.ui.screens.home.HomeBlue
import com.recifenews.app.ui.screens.home.TextPrimary
import com.recifenews.app.ui.screens.home.TextSecondary
import com.recifenews.app.ui.screens.home.categoryColor
import com.recifenews.app.ui.screens.home.imageResourceFor
import com.recifenews.app.ui.screens.home.initialsFrom
import com.recifenews.app.ui.screens.home.reactionColor
import com.recifenews.app.ui.screens.home.reactionCountsFor
import com.recifenews.app.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun FeedPostCard(
    post: FeedPost,
    user: UserProfile,
    reactions: List<ReactionOption>,
    interaction: PostInteraction,
    commentDraft: String,
    onReactionTrayToggle: () -> Unit,
    onReactionSelected: (String) -> Unit,
    onCommentToggle: () -> Unit,
    onCommentDraftChange: (String) -> Unit,
    onSendComment: () -> Unit,
    onShareToggle: () -> Unit,
    onShareSelected: (String) -> Unit,
    onSave: () -> Unit
) {
    val reactionCounts = reactionCountsFor(post, interaction)
    val selectedReaction = reactions.firstOrNull { it.id == interaction.reactionId }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        border = BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            PostHeader(post = post)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            PostImage(post = post)

            Spacer(modifier = Modifier.height(10.dp))

            PostStats(
                reactions = reactions,
                reactionCounts = reactionCounts,
                comments = interaction.comments,
                shares = interaction.shares
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = CardBorder)
            Spacer(modifier = Modifier.height(8.dp))

            PostActions(
                selectedReaction = selectedReaction,
                saved = interaction.saved,
                onReactionTrayToggle = onReactionTrayToggle,
                onCommentToggle = onCommentToggle,
                onShareToggle = onShareToggle,
                onSave = onSave
            )

            AnimatedVisibility(visible = interaction.showReactions) {
                ReactionTray(
                    reactions = reactions,
                    selectedReactionId = interaction.reactionId,
                    reactionCounts = reactionCounts,
                    onReactionSelected = onReactionSelected
                )
            }

            AnimatedVisibility(visible = interaction.showComments) {
                CommentComposer(
                    user = user,
                    value = commentDraft,
                    onValueChange = onCommentDraftChange,
                    onSend = onSendComment
                )
            }

            AnimatedVisibility(visible = interaction.showShareOptions) {
                ShareTray(onShareSelected = onShareSelected)
            }
        }
    }
}

@Composable
private fun PostHeader(post: FeedPost) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(
            initials = initialsFrom(post.author),
            color = categoryColor(post.category.id)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = post.author,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Black),
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (post.pinned) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(HomeBlue.copy(alpha = 0.1f))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "FIXADO",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                            color = HomeBlue
                        )
                    }
                }
            }
            Text(
                text = "${post.neighborhood} · ${post.timeLabel}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "Mais opções",
                tint = TextSecondary
            )
        }
    }
}

@Composable
private fun PostImage(post: FeedPost) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(218.dp)
            .clip(RoundedCornerShape(18.dp))
    ) {
        Image(
            painter = painterResource(imageResourceFor(post.imageKey)),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppColors.Transparent,
                            AppColors.Black.copy(alpha = 0.42f)
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AppColors.Black.copy(alpha = 0.44f))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = "AO VIVO",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                color = AppColors.White
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(AppColors.White)
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(categoryColor(post.category.id))
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = post.category.label,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun PostStats(
    reactions: List<ReactionOption>,
    reactionCounts: Map<String, Int>,
    comments: Int,
    shares: Int
) {
    val totalReactions = reactionCounts.values.sum()
    val topReactions = reactions.filter { (reactionCounts[it.id] ?: 0) > 0 }.take(3)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (topReactions.isEmpty()) {
                Text(
                    text = "Seja o primeiro a reagir",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            } else {
                topReactions.forEach { reaction ->
                    Text(
                        text = "${reaction.emoji} ${reactionCounts[reaction.id]}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = reactionColor(reaction.id)
                    )
                }
                Text(
                    text = "$totalReactions",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
        Text(
            text = "$comments comentários · $shares shares",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}

@Composable
private fun PostActions(
    selectedReaction: ReactionOption?,
    saved: Boolean,
    onReactionTrayToggle: () -> Unit,
    onCommentToggle: () -> Unit,
    onShareToggle: () -> Unit,
    onSave: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        ActionButton(
            text = selectedReaction?.let { "${it.emoji} ${it.label}" } ?: "Curtir",
            selected = selectedReaction != null,
            selectedColor = selectedReaction?.let { reactionColor(it.id) } ?: HomeBlue,
            onClick = onReactionTrayToggle,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            text = "Comentar",
            selected = false,
            onClick = onCommentToggle,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            text = "Compartilhar",
            selected = false,
            onClick = onShareToggle,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            text = if (saved) "Salvo" else "Salvar",
            selected = saved,
            selectedColor = HomeBlue,
            onClick = onSave,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = HomeBlue
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .background(if (selected) selectedColor.copy(alpha = 0.08f) else AppColors.Transparent)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = if (selected) selectedColor else AppColors.TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ReactionTray(
    reactions: List<ReactionOption>,
    selectedReactionId: String?,
    reactionCounts: Map<String, Int>,
    onReactionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(AppColors.White)
            .border(BorderStroke(1.dp, CardBorder), RoundedCornerShape(24.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        reactions.forEach { reaction ->
            val selected = selectedReactionId == reaction.id
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selected) reactionColor(reaction.id).copy(alpha = 0.12f) else AppColors.Transparent)
                    .clickable { onReactionSelected(reaction.id) }
                    .padding(horizontal = 7.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = reaction.emoji,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = (reactionCounts[reaction.id] ?: 0).toString(),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = reactionColor(reaction.id)
                )
            }
        }
    }
}

@Composable
private fun CommentComposer(
    user: UserProfile,
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(initials = initialsFrom(user.name), color = HomeBlue)
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Adicione um comentário...") },
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = HomeBlue.copy(alpha = 0.4f),
                unfocusedBorderColor = CardBorder,
                focusedContainerColor = FeedBackground,
                unfocusedContainerColor = FeedBackground
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .height(46.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (value.isBlank()) AppColors.DisabledContainer else HomeBlue)
                .clickable(enabled = value.isNotBlank(), onClick = onSend)
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Enviar",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black),
                color = if (value.isBlank()) AppColors.TextMuted else AppColors.White
            )
        }
    }
}

@Composable
private fun ShareTray(onShareSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(FeedBackground)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("WhatsApp", "Instagram", "Copiar link").forEach { channel ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(38.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(AppColors.White)
                    .border(BorderStroke(1.dp, CardBorder), RoundedCornerShape(14.dp))
                    .clickable { onShareSelected(channel) }
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = channel,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                    color = HomeBlue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
