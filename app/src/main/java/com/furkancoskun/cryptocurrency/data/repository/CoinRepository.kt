package com.furkancoskun.cryptocurrency.data.repository

import com.furkancoskun.cryptocurrency.data.api.CoinService
import javax.inject.Inject

class CoinRepository
@Inject
constructor(
    private val coinService: CoinService
) {
    suspend fun getCoinMarkets(targetCurrency: String) =
        coinService.getCoinMarkets(targetCurrency = targetCurrency)

    suspend fun getHistoricalPrice(id: String, targetCurrency: String, days: Int) =
        coinService.getHistoricalPrice(id = id, targetCurrency = targetCurrency, days = days)

}