package com.furkancoskun.cryptocurrency.data.api

import com.furkancoskun.cryptocurrency.data.model.response.GetCoinMarketResponse
import com.furkancoskun.cryptocurrency.data.model.response.HistoricalPriceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinService {

    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") targetCurrency: String
    ): List<GetCoinMarketResponse>

    @GET("coins/{id}/market_chart")
    suspend fun getHistoricalPrice(
        @Path("id") id: String,
        @Query("vs_currency") targetCurrency: String,
        @Query("days") days: Int
    ): HistoricalPriceResponse

}