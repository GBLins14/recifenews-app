package com.recifeemalerta.app.ui.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import com.recifeemalerta.app.feature.map.domain.model.MapCoordinate
import com.recifeemalerta.app.feature.map.domain.model.MapIncident
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIColor
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
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

    UIKitView(
        modifier = modifier,
        factory = {
            WKWebView(
                frame = CGRectMake(x = 0.0, y = 0.0, width = 0.0, height = 0.0),
                configuration = WKWebViewConfiguration()
            ).apply {
                opaque = false
                backgroundColor = UIColor.clearColor
                scrollView.bounces = false
                customUserAgent = "RecifeEmAlerta/1.0 iOS WKWebView"
                loadHTMLString(
                    string = html,
                    baseURL = NSURL.URLWithString("https://basemaps.cartocdn.com/")
                )
            }
        },
        update = { webView ->
            webView.loadHTMLString(
                string = html,
                baseURL = NSURL.URLWithString("https://basemaps.cartocdn.com/")
            )
        }
    )
}
