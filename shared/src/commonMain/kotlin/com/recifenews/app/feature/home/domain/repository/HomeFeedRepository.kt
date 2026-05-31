package com.recifenews.app.feature.home.domain.repository

import com.recifenews.app.feature.home.domain.model.CreatePostRequest
import com.recifenews.app.feature.home.domain.model.FeedPost
import com.recifenews.app.feature.home.domain.model.HomeFeedSnapshot

interface HomeFeedRepository {
    fun loadFeed(): HomeFeedSnapshot
    fun createPost(request: CreatePostRequest): FeedPost
}
