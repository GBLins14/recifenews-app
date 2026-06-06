package com.recifeemalerta.app.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

object AppIcons {
    val Add by lazyIcon("Add", "M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z")
    val ArrowBack by lazyIcon("ArrowBack", "M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.42-1.41L7.83 13H20v-2z")
    val Bookmark by lazyIcon("Bookmark", "M17 3H7c-1.1 0-2 .9-2 2v16l7-3 7 3V5c0-1.1-.9-2-2-2z")
    val BookmarkBorder by lazyIcon("BookmarkBorder", "M17 3H7c-1.1 0-2 .9-2 2v16l7-3 7 3V5c0-1.1-.9-2-2-2zm0 15-5-2.18L7 18V5h10v13z")
    val Campaign by lazyIcon("Campaign", "M18 11v2h4v-2h-4zM4 9v6h4l5 5V4L8 9H4zM16 5l1.4 1.4 2.8-2.8L18.8 2.2 16 5zM17.4 17.6 16 19l2.8 2.8 1.4-1.4-2.8-2.8z")
    val CheckCircle by lazyIcon("CheckCircle", "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z")
    val Close by lazyIcon("Close", "M18.3 5.7 12 12l6.3 6.3-1.4 1.4-6.3-6.3-6.3 6.3-1.4-1.4L9.2 12 2.9 5.7l1.4-1.4 6.3 6.3 6.3-6.3z")
    val Comment by lazyIcon("Comment", "M20 2H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h14l4 4V4c0-1.1-.9-2-2-2z")
    val DirectionsBus by lazyIcon("DirectionsBus", "M4 16c0 .88.39 1.67 1 2.22V20c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h8v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1.78c.61-.55 1-1.34 1-2.22V6c0-3.5-3.58-4-8-4s-8 .5-8 4v10zm3.5 1C6.67 17 6 16.33 6 15.5S6.67 14 7.5 14 9 14.67 9 15.5 8.33 17 7.5 17zm9 0c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zM6 6h12v5H6V6z")
    val Edit by lazyIcon("Edit", "M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34a1 1 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z")
    val Email by lazyIcon("Email", "M20 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4-8 5-8-5V6l8 5 8-5v2z")
    val Event by lazyIcon("Event", "M17 12h-5v5h5v-5zM16 1v2H8V1H6v2H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2h-1V1h-2zm3 18H5V8h14v11z")
    val FavoriteBorder by lazyIcon("FavoriteBorder", "M12.1 18.55l-.1.1-.11-.1C7.14 14.24 4 11.39 4 8.5 4 6.5 5.5 5 7.5 5c1.54 0 3.04.99 3.57 2.36h1.87C13.46 5.99 14.96 5 16.5 5 18.5 5 20 6.5 20 8.5c0 2.89-3.14 5.74-7.9 10.05zM16.5 3c-1.74 0-3.41.81-4.5 2.09C10.91 3.81 9.24 3 7.5 3 4.42 3 2 5.42 2 8.5c0 3.76 3.4 6.86 8.55 11.54L12 21.35l1.45-1.32C18.6 15.36 22 12.26 22 8.5 22 5.42 19.58 3 16.5 3z")
    val Groups by lazyIcon("Groups", "M16 11c1.66 0 3-1.34 3-3s-1.34-3-3-3-3 1.34-3 3 1.34 3 3 3zM8 11c1.66 0 3-1.34 3-3S9.66 5 8 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5C15 14.17 10.33 13 8 13zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z")
    val Home by lazyIcon("Home", "M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z")
    val KeyboardArrowDown by lazyIcon("KeyboardArrowDown", "M7.41 8.59 12 13.17l4.59-4.58L18 10l-6 6-6-6z")
    val KeyboardArrowRight by lazyIcon("KeyboardArrowRight", "M8.59 16.59 13.17 12 8.59 7.41 10 6l6 6-6 6z")
    val Layers by lazyIcon("Layers", "M11.99 18.54 4.62 12.8 3 14.07l9 7 9-7-1.63-1.27-7.38 5.74zM12 16l7.36-5.73L21 9l-9-7-9 7 1.63 1.27L12 16zm0-11.47L17.74 9 12 13.47 6.26 9 12 4.53z")
    val LocationOn by lazyIcon("LocationOn", "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5A2.5 2.5 0 1 1 12 6a2.5 2.5 0 0 1 0 5.5z")
    val Lock by lazyIcon("Lock", "M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zM9 8V6c0-1.66 1.34-3 3-3s3 1.34 3 3v2H9z")
    val MoreHoriz by lazyIcon("MoreHoriz", "M6 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z")
    val MyLocation by lazyIcon("MyLocation", "M13 2.05V0h-2v2.05C6.17 2.52 2.52 6.17 2.05 11H0v2h2.05c.47 4.83 4.12 8.48 8.95 8.95V24h2v-2.05c4.83-.47 8.48-4.12 8.95-8.95H24v-2h-2.05C21.48 6.17 17.83 2.52 13 2.05zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm0-12a4 4 0 1 0 0 8 4 4 0 0 0 0-8z")
    val Notifications by lazyIcon("Notifications", "M12 22c1.1 0 2-.9 2-2h-4c0 1.1.9 2 2 2zM18 16v-5c0-3.07-1.64-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5S10.5 3.17 10.5 4v.68C7.63 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2z")
    val Person by lazyIcon("Person", "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z")
    val Search by lazyIcon("Search", "M9.5 3a6.5 6.5 0 0 1 5.19 10.42l4.44 4.44-1.41 1.41-4.44-4.44A6.5 6.5 0 1 1 9.5 3zm0 2a4.5 4.5 0 1 0 0 9 4.5 4.5 0 0 0 0-9z")
    val Security by lazyIcon("Security", "M12 1 3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4z")
    val Send by lazyIcon("Send", "M2 21l21-9L2 3v7l15 2-15 2v7z")
    val Share by lazyIcon("Share", "M18 16.08c-.76 0-1.44.3-1.96.77L8.91 12.7c.05-.23.09-.46.09-.7s-.04-.47-.09-.7l7.05-4.11A3 3 0 1 0 15 5c0 .24.04.47.09.7L8.04 9.81A3 3 0 1 0 8.04 14.2l7.12 4.18c-.05.21-.08.43-.08.65A2.92 2.92 0 1 0 18 16.08z")
    val Storefront by lazyIcon("Storefront", "M21.9 8.89 20.85 4H3.15L2.1 8.89c-.24 1.12.39 2.21 1.43 2.69V20c0 .55.45 1 1 1h15c.55 0 1-.45 1-1v-8.42c1.04-.48 1.67-1.57 1.37-2.69zM5 19v-6h4v6H5zm12 0h-6v-6h6v6z")
    val Visibility by lazyIcon("Visibility", "M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zm0 12.5c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z")
    val VisibilityOff by lazyIcon("VisibilityOff", "M2 4.27 3.28 3 21 20.72 19.73 22l-3.02-3.02A11.6 11.6 0 0 1 12 19.5C7 19.5 2.73 16.39 1 12a12.9 12.9 0 0 1 4.31-5.54L2 4.27zM12 4.5c5 0 9.27 3.11 11 7.5a12.7 12.7 0 0 1-3.36 4.73L16.9 14A5 5 0 0 0 10 7.1L7.97 5.07A11.8 11.8 0 0 1 12 4.5z")
}

private fun lazyIcon(name: String, pathData: String): Lazy<ImageVector> = lazy {
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).addPath(
        pathData = addPathNodes(pathData),
        fill = SolidColor(Color.Black)
    ).build()
}
