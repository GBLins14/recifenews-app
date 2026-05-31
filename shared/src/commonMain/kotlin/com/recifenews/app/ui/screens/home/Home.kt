package com.recifenews.app.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recifenews.app.di.LocalAppDependencies
import com.recifenews.app.feature.home.domain.model.FeedTab
import com.recifenews.app.feature.home.ui.state.HomeStateHolder
import com.recifenews.app.feature.home.ui.state.HomeUiState
import com.recifenews.app.navigation.Screen
import com.recifenews.app.ui.screens.home.components.EmptyFeedState
import com.recifenews.app.ui.screens.home.components.FeedFeedback
import com.recifenews.app.ui.screens.home.components.FeedPostCard
import com.recifenews.app.ui.screens.home.components.FeedTabsCard
import com.recifenews.app.ui.screens.home.components.HomeHeader
import com.recifenews.app.ui.screens.home.components.NeighborhoodPickerCard
import com.recifenews.app.ui.screens.home.components.PinnedPostsCard
import com.recifenews.app.ui.screens.home.components.QuickPostComposer
import com.recifenews.app.ui.screens.home.components.SectionTitle
import com.recifenews.app.ui.preview.AppPreview

@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    val dependencies = LocalAppDependencies.current
    val stateHolder = remember(dependencies.homeFeedRepository) {
        HomeStateHolder(dependencies.homeFeedRepository)
    }

    HomeContent(
        state = stateHolder.state,
        actions = HomeActions(
            onSearchToggle = stateHolder::toggleSearch,
            onSearchChange = stateHolder::onSearchChanged,
            onNeighborhoodPickerToggle = stateHolder::toggleNeighborhoodPicker,
            onNeighborhoodSelected = stateHolder::selectNeighborhood,
            onTabSelected = stateHolder::selectTab,
            onPostTextChange = stateHolder::onPostTextChanged,
            onComposerCategorySelected = stateHolder::selectComposerCategory,
            onPublishPost = stateHolder::publishPost,
            onFeedbackDismiss = stateHolder::dismissFeedback,
            onReactionTrayToggle = stateHolder::toggleReactionTray,
            onReactionSelected = stateHolder::selectReaction,
            onCommentToggle = stateHolder::toggleComments,
            onCommentDraftChange = stateHolder::onCommentDraftChanged,
            onCommentSend = stateHolder::sendComment,
            onShareToggle = stateHolder::toggleShareOptions,
            onShareSelected = stateHolder::sharePost,
            onSaveToggle = stateHolder::toggleSave
        )
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    actions: HomeActions
) {
    Surface(
        color = FeedBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader(
                user = state.user,
                selectedNeighborhood = state.selectedNeighborhood,
                showSearch = state.showSearch,
                searchQuery = state.searchQuery,
                onSearchChange = actions.onSearchChange,
                onSearchToggle = actions.onSearchToggle,
                onNeighborhoodClick = actions.onNeighborhoodPickerToggle
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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
                    FeedTabsCard(
                        selectedTab = state.selectedTab,
                        onTabSelected = actions.onTabSelected
                    )
                }

                item {
                    QuickPostComposer(
                        user = state.user,
                        neighborhood = state.selectedNeighborhood,
                        value = state.newPostText,
                        categories = state.categories,
                        selectedCategoryId = state.selectedCategoryId,
                        onValueChange = actions.onPostTextChange,
                        onCategorySelected = actions.onComposerCategorySelected,
                        onPublish = actions.onPublishPost
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
                    PinnedPostsCard(posts = state.pinnedPosts)
                }

                item {
                    SectionTitle(
                        title = titleFor(state.selectedTab),
                        subtitle = "${state.selectedNeighborhood} · ${state.visiblePosts.size} publicações"
                    )
                }

                if (state.visiblePosts.isEmpty()) {
                    item {
                        EmptyFeedState(
                            searchQuery = state.searchQuery,
                            neighborhood = state.selectedNeighborhood
                        )
                    }
                }

                items(state.visiblePosts, key = { it.id }) { post ->
                    val interaction = state.interactions.getValue(post.id)

                    FeedPostCard(
                        post = post,
                        user = state.user,
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
                        onSendComment = { actions.onCommentSend(post.id) },
                        onShareToggle = { actions.onShareToggle(post.id) },
                        onShareSelected = { channel ->
                            actions.onShareSelected(post.id, channel)
                        },
                        onSave = { actions.onSaveToggle(post.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

private data class HomeActions(
    val onSearchToggle: () -> Unit,
    val onSearchChange: (String) -> Unit,
    val onNeighborhoodPickerToggle: () -> Unit,
    val onNeighborhoodSelected: (String) -> Unit,
    val onTabSelected: (FeedTab) -> Unit,
    val onPostTextChange: (String) -> Unit,
    val onComposerCategorySelected: (String) -> Unit,
    val onPublishPost: () -> Unit,
    val onFeedbackDismiss: () -> Unit,
    val onReactionTrayToggle: (Int) -> Unit,
    val onReactionSelected: (Int, String) -> Unit,
    val onCommentToggle: (Int) -> Unit,
    val onCommentDraftChange: (Int, String) -> Unit,
    val onCommentSend: (Int) -> Unit,
    val onShareToggle: (Int) -> Unit,
    val onShareSelected: (Int, String) -> Unit,
    val onSaveToggle: (Int) -> Unit
)

private fun titleFor(tab: FeedTab): String {
    return when (tab) {
        FeedTab.Todos -> "Feed ao vivo"
        FeedTab.Recentes -> "Mais recentes"
        FeedTab.EmAlta -> "Assuntos em alta"
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppPreview {
        HomeScreen(onNavigate = {}, onBack = {})
    }
}
