package com.recifeemalerta.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
