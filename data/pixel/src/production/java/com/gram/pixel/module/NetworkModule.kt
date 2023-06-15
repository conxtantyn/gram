package com.gram.pixel.module

import android.content.Context
import com.gram.pixel.R
import com.gram.pixel.model.Token
import com.gram.pixel.service.PhotoApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule(private val context: Context) {
    @Provides
    fun provideToken(): Token = Token(context.getString(R.string.apiKey))

    @Provides
    fun providePhotoApiService(): PhotoApiService = Retrofit
        .Builder()
        .baseUrl(context.getString(R.string.baseUrl))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PhotoApiService::class.java)
}
