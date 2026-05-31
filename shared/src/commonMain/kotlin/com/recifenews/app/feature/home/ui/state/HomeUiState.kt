package com.recifenews.app.feature.home.ui.state

import com.recifenews.app.feature.home.domain.model.FeedCategory
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.FeedTab
import com.recifenews.app.feature.home.domain.model.HomeFeedSnapshot
import com.recifenews.app.feature.home.domain.model.ReactionOption
import com.recifenews.app.feature.home.domain.model.UserProfile

data class HomeUiState(
    val user: UserProfile,
    val neighborhoods: List<String>,
    val categories: List<FeedCategory>,
    val reactions: List<ReactionOption>,
    val posts: List<FeedPost>,
    val interactions: Map<Int, PostInteraction>,
    val commentDrafts: Map<Int, String> = emptyMap(),
    val selectedNeighborhood: String,
    val selectedTab: FeedTab = FeedTab.Todos,
    val searchQuery: String = "",
    val showSearch: Boolean = false,
    val showNeighborhoodPicker: Boolean = false,
    val newPostText: String = "",
    val selectedCategoryId: String,
    val feedback: String? = null
) {
    val selectedCategory: FeedCategory
        get() = categories.firstOrNull { it.id == selectedCategoryId } ?: categories.first()

    val visiblePosts: List<FeedPost>
        get() {
            val searched = posts
                .filter { post -> post.neighborhood == selectedNeighborhood }
                .filter { post ->
                    val text = "${post.author} ${post.neighborhood} ${post.category.label} ${post.body}"
                    text.contains(searchQuery, ignoreCase = true)
                }

            return when (selectedTab) {
                FeedTab.Todos -> searched
                FeedTab.Recentes -> searched.sortedByDescending { it.id }
                FeedTab.EmAlta -> searched.sortedByDescending { post ->
                    val interaction = interactions[post.id]
                    post.trendScore + (interaction?.comments ?: 0) + (interaction?.shares ?: 0)
                }
            }
        }

    val pinnedPosts: List<FeedPost>
        get() = visiblePosts.filter { it.pinned }.take(2)

    companion object {
        fun from(snapshot: HomeFeedSnapshot): HomeUiState {
            return HomeUiState(
                user = snapshot.user,
                neighborhoods = snapshot.neighborhoods,
                categories = snapshot.categories,
                reactions = snapshot.reactions,
                posts = snapshot.posts,
                interactions = snapshot.posts.associate { post ->
                    post.id to PostInteraction(
                        comments = post.comments,
                        shares = post.shares
                    )
                },
                selectedNeighborhood = snapshot.user.mainNeighborhood,
                selectedCategoryId = snapshot.categories.first().id
            )
        }
    }
}

data class PostInteraction(
    val reactionId: String? = null,
    val saved: Boolean = false,
    val comments: Int = 0,
    val shares: Int = 0,
    val showReactions: Boolean = false,
    val showComments: Boolean = false,
    val showShareOptions: Boolean = false
)
