package com.recifenews.app.feature.map.domain.repository

import com.recifenews.app.feature.map.domain.model.LiveMapSnapshot

interface LiveMapRepository {
    fun loadLiveMap(): LiveMapSnapshot
}
