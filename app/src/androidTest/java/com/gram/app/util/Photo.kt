package com.gram.app.util

import com.gram.gallery.model.Location
import com.gram.gallery.model.Photo
import com.gram.gallery.model.Urls
import io.bloco.faker.Faker

private val faker = Faker()

val mockPhoto = Photo(
    id = "${System.currentTimeMillis()}",
    width = faker.number.positive(200, 400),
    height = faker.number.positive(200, 400),
    color = faker.color.hexColor(),
    likes = faker.number.positive(),
    description = faker.lorem.character(),
    urls = Urls(
        full = faker.placeholdit.image(),
        regular = faker.placeholdit.image()
    ),
    location = Location(
        name = faker.address.streetName(),
        city = faker.address.city(),
        country = faker.address.country(),
    )
)
