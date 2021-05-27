package com.furkancoskun.cryptocurrency.di.network.baseurl

import com.furkancoskun.cryptocurrency.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseURLModule {

    @BaseURL
    @Singleton
    @Provides
    fun provideBaseURL(): String = BuildConfig.BASE_URL

}