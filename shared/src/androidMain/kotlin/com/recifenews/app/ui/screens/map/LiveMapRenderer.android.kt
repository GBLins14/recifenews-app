package com.recifenews.app.ui.screens.map

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.recifenews.app.feature.map.domain.model.MapCoordinate
import com.recifenews.app.feature.map.domain.model.MapIncident

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal actual fun LiveMapRenderer(
    center: MapCoordinate,
    zoom: Int,
    incidents: List<MapIncident>,
    modifier: Modifier
) {
    val html = remember(center, zoom, incidents) {
        buildLiveMapHtml(center = center, zoom = zoom, incidents = incidents)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.userAgentString = "RecifeNews/1.0 Android WebView"
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                loadDataWithBaseURL(
                    "https://basemaps.cartocdn.com/",
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        },
        update = { webView ->
            if (webView.tag != html) {
                webView.tag = html
                webView.loadDataWithBaseURL(
                    "https://basemaps.cartocdn.com/",
                    html,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        }
    )
}
