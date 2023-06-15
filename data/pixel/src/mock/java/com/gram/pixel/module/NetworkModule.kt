package com.gram.pixel.module

import android.content.Context
import com.gram.pixel.R
import com.gram.pixel.model.Token
import com.gram.pixel.service.MockPhotoApiService
import com.gram.pixel.service.PhotoApiService
import dagger.Module
import dagger.Provides

@Module
class NetworkModule(private val context: Context) {
    @Provides
    fun provideToken(): Token = Token(context.getString(R.string.apiKey))

    @Provides
    fun providePhotoApiService(): PhotoApiService = MockPhotoApiService()
}
