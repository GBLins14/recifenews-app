package com.recifeemalerta.app.ui.screens.home

import com.recifeemalerta.app.ui.icons.AppIcons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.di.LocalAppDependencies
import com.recifeemalerta.app.feature.home.domain.model.FeedCategory
import com.recifeemalerta.app.feature.home.domain.model.FeedPost
import com.recifeemalerta.app.feature.home.domain.model.PostImageKey
import com.recifeemalerta.app.feature.home.domain.model.ReactionOption
import com.recifeemalerta.app.feature.home.ui.state.HomeNavigationItem
import com.recifeemalerta.app.feature.home.ui.state.HomeStateHolder
import com.recifeemalerta.app.feature.home.ui.state.HomeUiState
import com.recifeemalerta.app.feature.home.ui.state.PostInteraction
import com.recifeemalerta.app.feature.map.ui.state.LiveMapStateHolder
import com.recifeemalerta.app.feature.map.ui.state.LiveMapUiState
import com.recifeemalerta.app.navigation.Screen
import com.recifeemalerta.app.ui.screens.home.components.FeedFeedback
import com.recifeemalerta.app.ui.screens.home.components.HomeBottomNavigation
import com.recifeemalerta.app.ui.screens.home.components.NeighborhoodPickerCard
import com.recifeemalerta.app.ui.screens.home.components.QuickPostComposer
import com.recifeemalerta.app.ui.screens.map.LiveMapActions
import com.recifeemalerta.app.ui.screens.map.LiveMapPanel
import com.recifeemalerta.app.ui.preview.AppPreview
import org.jetbrains.compose.resources.painterResource
import recifeemalerta.shared.generated.resources.Res
import recifeemalerta.shared.generated.resources.banner

@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.homeFeedRepository) {
        HomeStateHolder(dependencies.homeFeedRepository)
    }
    val mapStateHolder = remember(dependencies.liveMapRepository) {
        LiveMapStateHolder(dependencies.liveMapRepository)
    }

    HomeContent(
        state = stateHolder.state,
        mapState = mapStateHolder.state,
        actions = HomeActions(
            onSearchToggle = stateHolder::toggleSearchPanel,
            onSearchChange = stateHolder::onSearchChanged,
            onNeighborhoodPickerToggle = stateHolder::toggleNeighborhoodPicker,
            onNeighborhoodSelected = stateHolder::selectNeighborhood,
            onNavigationSelected = stateHolder::selectNavigationItem,
            onFeedCategorySelected = stateHolder::selectFeedCategory,
            onComposerToggle = stateHolder::toggleComposer,
            onPostTextChange = stateHolder::onPostTextChanged,
            onComposerCategorySelected = stateHolder::selectComposerCategory,
            onPublishPost = stateHolder::publishPost,
            onFeedbackDismiss = stateHolder::dismissFeedback,
            onOfferClick = stateHolder::showOfferComingSoon,
            onReactionTrayToggle = stateHolder::toggleReactionTray,
            onReactionSelected = stateHolder::selectReaction,
            onCommentToggle = stateHolder::toggleComments,
            onCommentDraftChange = stateHolder::onCommentDraftChanged,
            onCommentSend = stateHolder::sendComment,
            onShareToggle = stateHolder::toggleShareOptions,
            onShareSelected = stateHolder::sharePost,
            onSaveToggle = stateHolder::toggleSave
        ),
        mapActions = LiveMapActions(
            onFilterSelected = mapStateHolder::selectFilter,
            onSearchToggle = mapStateHolder::toggleSearch,
            onSearchChange = mapStateHolder::onSearchChanged,
            onRefresh = mapStateHolder::refresh
        )
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    mapState: LiveMapUiState,
    actions: HomeActions,
    mapActions: LiveMapActions
) {
    val colors = homePalette()
    val showMap = state.selectedNavigationItem == HomeNavigationItem.Neighborhoods &&
        !state.showNeighborhoodPicker &&
        !state.showComposer &&
        !state.showSearchPanel

    Surface(
        color = colors.feedBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            containerColor = colors.feedBackground,
            contentWindowInsets = WindowInsets.statusBars.only(WindowInsetsSides.Top),
            bottomBar = {
                HomeBottomNavigation(
                    selectedItem = state.selectedNavigationItem,
                    onItemSelected = actions.onNavigationSelected
                )
            }
        ) { innerPadding ->
            if (showMap) {
                LiveMapPanel(
                    state = mapState,
                    actions = mapActions,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
                return@Scaffold
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(start = 15.dp, top = 14.dp, end = 15.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                item {
                    CompactHomeTopBar(
                        selectedNeighborhood = state.selectedNeighborhood,
                        onNeighborhoodClick = actions.onNeighborhoodPickerToggle,
                        onSearchClick = actions.onSearchToggle,
                        onNotificationsClick = { actions.onNavigationSelected(HomeNavigationItem.Alerts) }
                    )
                }

                item {
                    AnimatedVisibility(visible = state.showSearchPanel) {
                        CompactSearchField(
                            query = state.searchQuery,
                            onQueryChange = actions.onSearchChange
                        )
                    }
                }

                item {
                    AnimatedVisibility(visible = state.showNeighborhoodPicker) {
                        NeighborhoodPickerCard(
                            user = state.user,
                            neighborhoods = state.neighborhoods,
                            selectedNeighborhood = state.selectedNeighborhood,
                            onNeighborhoodSelected = actions.onNeighborhoodSelected
                        )
                    }
                }

                item {
                    SituationCard(status = "Perigo")
                }

                item {
                    OfferCard(
                        title = "Açaí da Bru",
                        onClick = actions.onOfferClick
                    )
                }

                item {
                    AnimatedVisibility(visible = state.feedback != null) {
                        state.feedback?.let { message ->
                            FeedFeedback(message = message, onClose = actions.onFeedbackDismiss)
                        }
                    }
                }

                item {
                    CompactSectionTitle(text = "Categorias")
                }

                item {
                    CategoryStrip(
                        categories = state.categories,
                        selectedCategoryId = state.selectedFeedCategoryId,
                        onCategorySelected = actions.onFeedCategorySelected
                    )
                }

                item {
                    CompactSectionTitle(text = "Comunidade")
                }

                if (state.visiblePosts.isEmpty()) {
                    item {
                        CommunityPlaceholderCard(text = "Nenhuma atualização em ${state.selectedNeighborhood}.")
                    }
                }

                items(state.visiblePosts.take(4), key = { it.id }) { post ->
                    val interaction = state.interactions.getValue(post.id)

                    CommunityPostCard(
                        post = post,
                        reactions = state.reactions,
                        interaction = interaction,
                        commentDraft = state.commentDrafts[post.id].orEmpty(),
                        onReactionTrayToggle = { actions.onReactionTrayToggle(post.id) },
                        onReactionSelected = { reactionId ->
                            actions.onReactionSelected(post.id, reactionId)
                        },
                        onCommentToggle = { actions.onCommentToggle(post.id) },
                        onCommentDraftChange = { draft ->
                            actions.onCommentDraftChange(post.id, draft)
                        },
                        onCommentSend = { actions.onCommentSend(post.id) },
                        onShareToggle = { actions.onShareToggle(post.id) },
                        onShareSelected = { channel ->
                            actions.onShareSelected(post.id, channel)
                        },
                        onSaveToggle = { actions.onSaveToggle(post.id) }
                    )
                }

                item {
                    AnimatedVisibility(visible = state.showComposer) {
                        QuickPostComposer(
                            user = state.user,
                            neighborhood = state.selectedNeighborhood,
                            expanded = true,
                            value = state.newPostText,
                            categories = state.categories,
                            selectedCategoryId = state.selectedCategoryId,
                            onExpand = actions.onComposerToggle,
                            onValueChange = actions.onPostTextChange,
                            onCategorySelected = actions.onComposerCategorySelected,
                            onPublish = actions.onPublishPost
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactHomeTopBar(
    selectedNeighborhood: String,
    onNeighborhoodClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    val colors = homePalette()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onNeighborhoodClick),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = selectedNeighborhood,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = AppIcons.KeyboardArrowDown,
                    contentDescription = null,
                    tint = colors.textSecondary,
                    modifier = Modifier.size(17.dp)
                )
            }
            Row(
                modifier = Modifier.height(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(colors.accentStrong)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "bairro ao vivo",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    color = colors.textSecondary,
                    maxLines = 1
                )
            }
        }

        HeaderIconButton(
            icon = AppIcons.Search,
            contentDescription = "Buscar",
            onClick = onSearchClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        HeaderIconButton(
            icon = AppIcons.Notifications,
            contentDescription = "Notificações",
            onClick = onNotificationsClick,
            showBadge = true
        )
    }
}

@Composable
private fun HeaderIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    showBadge: Boolean = false
) {
    val colors = homePalette()

    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(colors.card)
            .border(BorderStroke(1.dp, colors.cardBorder), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colors.textPrimary,
            modifier = Modifier.size(21.dp)
        )
        if (showBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
            )
        }
    }
}

@Composable
private fun CompactSearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    val colors = homePalette()

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        placeholder = {
            Text(
                text = "Buscar notícias, alertas e moradores",
                style = MaterialTheme.typography.bodySmall,
                color = colors.textMuted
            )
        },
        leadingIcon = {
            Icon(
                imageVector = AppIcons.Search,
                contentDescription = null,
                tint = colors.textSecondary,
                modifier = Modifier.size(19.dp)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary,
            cursorColor = colors.accentStrong,
            focusedBorderColor = colors.accentStrong,
            unfocusedBorderColor = colors.cardBorder,
            focusedContainerColor = colors.card,
            unfocusedContainerColor = colors.card
        )
    )
}

@Composable
private fun SituationCard(status: String) {
    val colors = homePalette()
    val error = MaterialTheme.colorScheme.error
    val shape = RoundedCornerShape(16.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = shape,
        color = colors.card,
        border = BorderStroke(1.dp, error.copy(alpha = 0.28f)),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            error.copy(alpha = 0.2f),
                            colors.card
                        )
                    )
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(error.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.Notifications,
                    contentDescription = null,
                    tint = error,
                    modifier = Modifier.size(21.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = buildAnnotatedString {
                        append("Situação: ")
                        withStyle(SpanStyle(color = error, fontWeight = FontWeight.Black)) {
                            append(status)
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "A quantidade de assaltos no bairro subiu 25% nos ultimos 7 dias.",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun OfferCard(
    title: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)

    Image(
        painter = painterResource(Res.drawable.banner),
        contentDescription = "Banner patrocinado $title",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2290f / 687f)
            .clip(shape)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun CompactSectionTitle(text: String) {
    val colors = homePalette()

    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
        color = colors.textPrimary,
        maxLines = 1
    )
}

@Composable
private fun CategoryStrip(
    categories: List<FeedCategory>,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.CenterHorizontally)
    ) {
        CategoryBlock(
            label = "Todos",
            icon = AppIcons.Campaign,
            color = homePalette().accentStrong,
            selected = selectedCategoryId == null,
            onClick = { onCategorySelected(null) }
        )
        categories.forEach { category ->
            CategoryBlock(
                label = category.label,
                icon = iconForCategory(category.id),
                color = categoryColor(category.id),
                selected = selectedCategoryId == category.id,
                onClick = { onCategorySelected(category.id) }
            )
        }
    }
}

@Composable
private fun CategoryBlock(
    label: String,
    icon: ImageVector,
    color: androidx.compose.ui.graphics.Color,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = homePalette()
    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            .height(50.dp)
            .clip(shape)
            .background(if (selected) colors.accentStrong.copy(alpha = 0.14f) else colors.card)
            .border(
                BorderStroke(1.dp, if (selected) colors.accentStrong.copy(alpha = 0.5f) else colors.cardBorder),
                shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(31.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color.copy(alpha = if (selected) 0.22f else 0.13f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(9.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
            color = colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CommunityPostCard(
    post: FeedPost,
    reactions: List<ReactionOption>,
    interaction: PostInteraction,
    commentDraft: String,
    onReactionTrayToggle: () -> Unit,
    onReactionSelected: (String) -> Unit,
    onCommentToggle: () -> Unit,
    onCommentDraftChange: (String) -> Unit,
    onCommentSend: () -> Unit,
    onShareToggle: () -> Unit,
    onShareSelected: (String) -> Unit,
    onSaveToggle: () -> Unit
) {
    val colors = homePalette()
    val reactionCounts = reactionCountsFor(post, interaction)
    val totalReactions = reactionCounts.values.sum()
    val selectedReaction = reactions.firstOrNull { it.id == interaction.reactionId }
    val shape = RoundedCornerShape(18.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 136.dp),
        shape = shape,
        color = colors.card,
        border = BorderStroke(1.dp, colors.cardBorder),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(11.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                AvatarBadge(name = post.author)

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    PostCompactHeader(post = post)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textPrimary,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    Image(
                        painter = painterResource(imageResourceFor(post.imageKey)),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 96.dp, height = 76.dp)
                            .clip(RoundedCornerShape(15.dp))
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(colors.card.copy(alpha = 0.86f))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = post.category.label,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                            color = categoryColor(post.category.id),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(9.dp))

            PostCompactActions(
                totalReactions = totalReactions,
                comments = interaction.comments,
                shares = interaction.shares,
                saved = interaction.saved,
                selectedReaction = selectedReaction,
                onReactionTrayToggle = onReactionTrayToggle,
                onCommentToggle = onCommentToggle,
                onShareToggle = onShareToggle,
                onSaveToggle = onSaveToggle
            )

            AnimatedVisibility(visible = interaction.showReactions) {
                ReactionPicker(
                    reactions = reactions,
                    selectedReactionId = interaction.reactionId,
                    reactionCounts = reactionCounts,
                    onReactionSelected = onReactionSelected
                )
            }

            AnimatedVisibility(visible = interaction.showComments) {
                CompactCommentBox(
                    value = commentDraft,
                    onValueChange = onCommentDraftChange,
                    onSend = onCommentSend
                )
            }

            AnimatedVisibility(visible = interaction.showShareOptions) {
                CompactShareOptions(onShareSelected = onShareSelected)
            }
        }
    }
}

@Composable
private fun AvatarBadge(name: String) {
    val colors = homePalette()

    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(colors.accentStrong.copy(alpha = 0.13f))
            .border(BorderStroke(1.dp, colors.accentStrong.copy(alpha = 0.18f)), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initialsFrom(name),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
            color = colors.accentStrong,
            maxLines = 1
        )
    }
}

@Composable
private fun PostCompactHeader(post: FeedPost) {
    val colors = homePalette()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = post.author,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Black),
            color = colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )
        Text(
            text = " · ${post.timeLabel}",
            style = MaterialTheme.typography.labelSmall,
            color = colors.textSecondary,
            maxLines = 1
        )
    }

    Spacer(modifier = Modifier.height(2.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(categoryColor(post.category.id))
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "${post.category.label} · ${post.neighborhood}",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PostCompactActions(
    totalReactions: Int,
    comments: Int,
    shares: Int,
    saved: Boolean,
    selectedReaction: ReactionOption?,
    onReactionTrayToggle: () -> Unit,
    onCommentToggle: () -> Unit,
    onShareToggle: () -> Unit,
    onSaveToggle: () -> Unit
) {
    val colors = homePalette()
    val selectedReactionColor = selectedReaction?.let { reactionColor(it.id) } ?: colors.textSecondary

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onReactionTrayToggle),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedReaction == null) {
                Icon(
                    imageVector = AppIcons.FavoriteBorder,
                    contentDescription = "Reagir",
                    tint = colors.textSecondary,
                    modifier = Modifier.size(17.dp)
                )
            } else {
                Text(
                    text = selectedReaction.emoji,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = totalReactions.toString(),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = selectedReactionColor
            )
        }

        IconMetric(
            icon = AppIcons.Comment,
            value = comments,
            contentDescription = "Comentários",
            onClick = onCommentToggle
        )

        IconMetric(
            icon = AppIcons.Share,
            value = shares,
            contentDescription = "Compartilhar",
            onClick = onShareToggle
        )

        Icon(
            imageVector = if (saved) AppIcons.Bookmark else AppIcons.BookmarkBorder,
            contentDescription = if (saved) "Remover dos salvos" else "Salvar",
            tint = if (saved) colors.accentStrong else colors.textSecondary,
            modifier = Modifier
                .size(17.dp)
                .clickable(onClick = onSaveToggle)
        )
    }
}

@Composable
private fun IconMetric(
    icon: ImageVector,
    value: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    val colors = homePalette()

    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colors.textSecondary,
            modifier = Modifier.size(17.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = colors.textSecondary
        )
    }
}

@Composable
private fun ReactionPicker(
    reactions: List<ReactionOption>,
    selectedReactionId: String?,
    reactionCounts: Map<String, Int>,
    onReactionSelected: (String) -> Unit
) {
    val colors = homePalette()

    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        reactions.forEach { reaction ->
            val selected = reaction.id == selectedReactionId
            Row(
                modifier = Modifier
                    .height(28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (selected) reactionColor(reaction.id).copy(alpha = 0.18f)
                        else colors.card.copy(alpha = 0.72f)
                    )
                    .border(BorderStroke(1.dp, colors.cardBorder), RoundedCornerShape(14.dp))
                    .clickable { onReactionSelected(reaction.id) }
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = reaction.emoji, style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = (reactionCounts[reaction.id] ?: 0).toString(),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = reactionColor(reaction.id)
                )
            }
        }
    }
}

@Composable
private fun CompactCommentBox(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    val colors = homePalette()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Adicionar comentário",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textPrimary,
                unfocusedTextColor = colors.textPrimary,
                cursorColor = colors.accentStrong,
                focusedBorderColor = colors.accentStrong,
                unfocusedBorderColor = colors.cardBorder,
                focusedContainerColor = colors.card.copy(alpha = 0.72f),
                unfocusedContainerColor = colors.card.copy(alpha = 0.72f)
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Box(
            modifier = Modifier
                .height(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (value.isBlank()) colors.card.copy(alpha = 0.72f) else colors.accentStrong)
                .clickable(enabled = value.isNotBlank(), onClick = onSend)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Enviar",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                color = if (value.isBlank()) colors.textMuted else colors.onAccent
            )
        }
    }
}

@Composable
private fun CompactShareOptions(onShareSelected: (String) -> Unit) {
    val colors = homePalette()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        listOf("WhatsApp", "Instagram", "Copiar link").forEach { channel ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.card.copy(alpha = 0.72f))
                    .border(BorderStroke(1.dp, colors.cardBorder), RoundedCornerShape(8.dp))
                    .clickable { onShareSelected(channel) }
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = channel,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = colors.accentStrong,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun iconForCategory(categoryId: String): ImageVector {
    return when (categoryId) {
        "traffic" -> AppIcons.DirectionsBus
        "alerts" -> AppIcons.Notifications
        "culture" -> AppIcons.Event
        "community" -> AppIcons.Groups
        else -> AppIcons.Campaign
    }
}

@Composable
private fun CommunityPlaceholderCard(text: String) {
    val colors = homePalette()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(colors.accentStrong.copy(alpha = 0.22f))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = colors.textSecondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private data class HomeActions(
    val onSearchToggle: () -> Unit,
    val onSearchChange: (String) -> Unit,
    val onNeighborhoodPickerToggle: () -> Unit,
    val onNeighborhoodSelected: (String) -> Unit,
    val onNavigationSelected: (HomeNavigationItem) -> Unit,
    val onFeedCategorySelected: (String?) -> Unit,
    val onComposerToggle: () -> Unit,
    val onPostTextChange: (String) -> Unit,
    val onComposerCategorySelected: (String) -> Unit,
    val onPublishPost: () -> Unit,
    val onFeedbackDismiss: () -> Unit,
    val onOfferClick: () -> Unit,
    val onReactionTrayToggle: (Int) -> Unit,
    val onReactionSelected: (Int, String) -> Unit,
    val onCommentToggle: (Int) -> Unit,
    val onCommentDraftChange: (Int, String) -> Unit,
    val onCommentSend: (Int) -> Unit,
    val onShareToggle: (Int) -> Unit,
    val onShareSelected: (Int, String) -> Unit,
    val onSaveToggle: (Int) -> Unit
)

@Preview
@Composable
fun HomeScreenPreview() {
    AppPreview {
        HomeScreen(onNavigate = {}, onBack = {})
    }
}
