package com.recifeemalerta.app.feature.home.domain.repository

import com.recifeemalerta.app.feature.home.domain.model.CreatePostRequest
import com.recifeemalerta.app.feature.home.domain.model.FeedPost
import com.recifeemalerta.app.feature.home.domain.model.HomeFeedSnapshot

interface HomeFeedRepository {
    fun loadFeed(): HomeFeedSnapshot
    fun createPost(request: CreatePostRequest): FeedPost
}
