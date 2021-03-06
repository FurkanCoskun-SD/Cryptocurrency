package com.furkancoskun.cryptocurrency.di.network

import com.furkancoskun.cryptocurrency.di.network.baseurl.BaseURL
import com.furkancoskun.cryptocurrency.di.network.okhttp.AppServiceInterceptorOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @AppServiceRetrofitInstance
    @Singleton
    @Provides
    fun provideAppServiceRetrofitInstance(
        @BaseURL baseUrl: String,
        converterFactory: Converter.Factory,
        @AppServiceInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

}