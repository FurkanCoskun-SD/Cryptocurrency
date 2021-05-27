package com.furkancoskun.cryptocurrency.di.api

import com.furkancoskun.cryptocurrency.data.api.CoinService
import com.furkancoskun.cryptocurrency.di.network.AppServiceRetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideCoinService(@AppServiceRetrofitInstance retrofit: Retrofit): CoinService {
        return retrofit.create(CoinService::class.java)
    }

}