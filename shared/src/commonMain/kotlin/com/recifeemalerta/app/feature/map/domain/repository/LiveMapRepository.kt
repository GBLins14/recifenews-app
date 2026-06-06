package com.recifeemalerta.app.feature.map.domain.repository

import com.recifeemalerta.app.feature.map.domain.model.LiveMapSnapshot

interface LiveMapRepository {
    fun loadLiveMap(): LiveMapSnapshot
}
