package com.gram.app.ui.gallery.photo

import com.gram.gallery.model.Photo

fun Photo.mapToDomain(): PhotoArgs {
    return PhotoArgs(
        id = id,
        width = width,
        height = height,
        color = color,
        likes = likes,
        description = description,
        urls = PhotoArgs.Urls(
            full = urls.full,
            regular = urls.regular,
        ),
        location = PhotoArgs.Location(
            name = location.name,
            city = location.city,
            country = location.country,
        )
    )
}
