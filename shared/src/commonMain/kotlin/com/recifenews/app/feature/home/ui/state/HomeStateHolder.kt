package com.recifenews.app.feature.home.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.recifenews.app.feature.home.domain.model.CreatePostRequest
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.FeedTab
import com.recifenews.app.feature.home.domain.repository.HomeFeedRepository

class HomeStateHolder(
    private val repository: HomeFeedRepository
) {
    var state by mutableStateOf(HomeUiState.from(repository.loadFeed()))
        private set

    fun toggleSearchPanel() {
        val nextVisibility = !state.showSearchPanel
        state = state.copy(
            showSearchPanel = nextVisibility,
            searchQuery = if (nextVisibility) state.searchQuery else "",
            showNeighborhoodPicker = false,
            showComposer = false,
            selectedNavigationItem = HomeNavigationItem.Home
        )
    }

    fun onSearchChanged(query: String) {
        state = state.copy(
            searchQuery = query,
            showSearchPanel = true
        )
    }

    fun toggleNeighborhoodPicker() {
        val nextVisibility = !state.showNeighborhoodPicker
        state = state.copy(
            selectedNavigationItem = if (nextVisibility) HomeNavigationItem.Neighborhoods else HomeNavigationItem.Home,
            showNeighborhoodPicker = nextVisibility,
            showSearchPanel = false,
            showComposer = false
        )
    }

    fun selectNavigationItem(item: HomeNavigationItem) {
        state = when (item) {
            HomeNavigationItem.Home -> state.copy(
                selectedNavigationItem = item,
                showNeighborhoodPicker = false,
                showSearchPanel = false,
                showComposer = false
            )
            HomeNavigationItem.Community -> state.copy(
                selectedNavigationItem = item,
                showNeighborhoodPicker = false,
                showSearchPanel = false,
                showComposer = false
            )
            HomeNavigationItem.Neighborhoods -> state.copy(
                selectedNavigationItem = item,
                showNeighborhoodPicker = !state.showNeighborhoodPicker,
                showSearchPanel = false,
                showComposer = false
            )
            HomeNavigationItem.Alerts -> state.copy(
                selectedNavigationItem = item,
                showNeighborhoodPicker = false,
                showSearchPanel = false,
                showComposer = false,
                feedback = "Central de alertas em breve."
            )
            HomeNavigationItem.Profile -> state.copy(
                selectedNavigationItem = item,
                showNeighborhoodPicker = false,
                showSearchPanel = false,
                showComposer = false,
                feedback = "Perfil em breve."
            )
            HomeNavigationItem.NewPost -> toggleComposerState()
        }
    }

    fun toggleComposer() {
        state = toggleComposerState()
    }

    fun selectNeighborhood(neighborhood: String) {
        state = state.copy(
            selectedNeighborhood = neighborhood,
            showNeighborhoodPicker = false,
            showSearchPanel = false,
            selectedNavigationItem = HomeNavigationItem.Home,
            searchQuery = ""
        )
    }

    fun selectTab(tab: FeedTab) {
        state = state.copy(selectedTab = tab)
    }

    fun selectFeedCategory(categoryId: String?) {
        state = state.copy(
            selectedFeedCategoryId = if (state.selectedFeedCategoryId == categoryId) null else categoryId,
            selectedTab = FeedTab.Todos
        )
    }

    fun onPostTextChanged(text: String) {
        state = state.copy(newPostText = text)
    }

    fun selectComposerCategory(categoryId: String) {
        state = state.copy(selectedCategoryId = categoryId)
    }

    fun publishPost() {
        val body = state.newPostText.trim()
        if (body.isEmpty()) return

        val post = repository.createPost(
            CreatePostRequest(
                author = state.user.name,
                neighborhood = state.selectedNeighborhood,
                categoryId = state.selectedCategoryId,
                body = body,
                nextId = nextPostId()
            )
        )

        state = state.copy(
            posts = listOf(post) + state.posts,
            interactions = state.interactions + (post.id to PostInteraction()),
            newPostText = "",
            selectedTab = FeedTab.Todos,
            selectedNavigationItem = HomeNavigationItem.Home,
            showComposer = false,
            feedback = "Publicação criada em ${state.selectedNeighborhood}."
        )
    }

    fun dismissFeedback() {
        state = state.copy(feedback = null)
    }

    fun showOfferComingSoon() {
        state = state.copy(feedback = "Ver agora em breve.")
    }

    fun toggleReactionTray(postId: Int) {
        updateInteraction(postId) { interaction ->
            interaction.copy(
                showReactions = !interaction.showReactions,
                showComments = false,
                showShareOptions = false
            )
        }
    }

    fun selectReaction(postId: Int, reactionId: String) {
        updateInteraction(postId) { interaction ->
            interaction.copy(
                reactionId = if (interaction.reactionId == reactionId) null else reactionId,
                showReactions = false
            )
        }
    }

    fun toggleComments(postId: Int) {
        updateInteraction(postId) { interaction ->
            interaction.copy(
                showComments = !interaction.showComments,
                showReactions = false,
                showShareOptions = false
            )
        }
    }

    fun onCommentDraftChanged(postId: Int, draft: String) {
        state = state.copy(commentDrafts = state.commentDrafts + (postId to draft))
    }

    fun sendComment(postId: Int) {
        val draft = state.commentDrafts[postId].orEmpty().trim()
        if (draft.isEmpty()) return

        updateInteraction(postId) { interaction ->
            interaction.copy(
                comments = interaction.comments + 1,
                showComments = true
            )
        }
        state = state.copy(
            commentDrafts = state.commentDrafts + (postId to ""),
            feedback = "Comentário publicado."
        )
    }

    fun toggleShareOptions(postId: Int) {
        updateInteraction(postId) { interaction ->
            interaction.copy(
                showShareOptions = !interaction.showShareOptions,
                showComments = false,
                showReactions = false
            )
        }
    }

    fun sharePost(postId: Int, channel: String) {
        updateInteraction(postId) { interaction ->
            interaction.copy(
                shares = interaction.shares + 1,
                showShareOptions = false
            )
        }
        state = state.copy(feedback = "Post compartilhado via $channel.")
    }

    fun toggleSave(postId: Int) {
        val interaction = interactionFor(postId)
        updateInteraction(postId) { current ->
            current.copy(saved = !current.saved)
        }
        state = state.copy(
            feedback = if (interaction.saved) "Post removido dos salvos." else "Post salvo."
        )
    }

    private fun updateInteraction(
        postId: Int,
        transform: (PostInteraction) -> PostInteraction
    ) {
        val updated = transform(interactionFor(postId))
        state = state.copy(interactions = state.interactions + (postId to updated))
    }

    private fun interactionFor(postId: Int): PostInteraction {
        return state.interactions[postId] ?: state.posts
            .firstOrNull { it.id == postId }
            ?.toInteraction()
        ?: PostInteraction()
    }

    private fun FeedPost.toInteraction(): PostInteraction {
        return PostInteraction(comments = comments, shares = shares)
    }

    private fun nextPostId(): Int {
        return (state.posts.maxOfOrNull { it.id } ?: 0) + 1
    }

    private fun toggleComposerState(): HomeUiState {
        val nextVisibility = !state.showComposer
        return state.copy(
            selectedNavigationItem = HomeNavigationItem.Home,
            showComposer = nextVisibility,
            showSearchPanel = false,
            showNeighborhoodPicker = false
        )
    }
}
