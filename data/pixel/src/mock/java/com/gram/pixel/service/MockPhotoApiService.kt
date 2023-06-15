package com.gram.pixel.service

import io.bloco.faker.Faker

class MockPhotoApiService : PhotoApiService {

    private val faker = Faker()

    override suspend fun getPhoto(key: String): PhotoApiService.Photo {
        return PhotoApiService.Photo(
            id = "${System.currentTimeMillis()}",
            width = faker.number.positive(200, 400),
            height = faker.number.positive(200, 400),
            color = faker.color.hexColor(),
            description = faker.lorem.character(),
            likes = faker.number.positive(),
            urls = PhotoApiService.Url(
                full = faker.avatar.image(),
                regular = faker.avatar.image()
            ),
            location = PhotoApiService.Location(
                name = faker.address.streetName(),
                city = faker.address.city(),
                country = faker.address.country(),
            )
        )
    }
}
