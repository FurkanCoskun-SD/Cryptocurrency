package com.furkancoskun.cryptocurrency.di.repository

import com.furkancoskun.cryptocurrency.data.api.CoinService
import com.furkancoskun.cryptocurrency.data.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCoinRepository(coinService: CoinService): CoinRepository {
        return CoinRepository(coinService)
    }

}