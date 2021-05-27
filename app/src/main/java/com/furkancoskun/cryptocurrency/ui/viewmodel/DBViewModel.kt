package com.furkancoskun.cryptocurrency.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.furkancoskun.cryptocurrency.data.room.CoinFavoritesListEntity
import com.furkancoskun.cryptocurrency.data.room.CoinsListEntity
import com.furkancoskun.cryptocurrency.data.repository.DBRepository

class DBViewModel : ViewModel() {

    var liveDataCoinsListEntity: LiveData<List<CoinsListEntity>>? = null
    var liveDataCoinFavoritesListEntity: LiveData<List<CoinFavoritesListEntity>>? = null

    fun addCoinMarkets(context: Context, coinsListEntity: List<CoinsListEntity>) {
        DBRepository.addCoinMarkets(context, coinsListEntity)
    }

    fun getCoinsMarkets(context: Context): LiveData<List<CoinsListEntity>>? {
        liveDataCoinsListEntity = DBRepository.getCoinsMarkets(context)
        return liveDataCoinsListEntity
    }

    fun searchCoinsMarkets(context: Context, symbol: String): LiveData<List<CoinsListEntity>>? {
        liveDataCoinsListEntity = DBRepository.searchCoinsMarkets(context, symbol)
        return liveDataCoinsListEntity
    }

    fun addFavorites(context: Context, coinFavoritesListEntity: List<CoinFavoritesListEntity>) {
        DBRepository.addFavorites(context, coinFavoritesListEntity)
    }

    fun getFavorites(context: Context): LiveData<List<CoinFavoritesListEntity>>? {
        liveDataCoinFavoritesListEntity = DBRepository.getFavorites(context)
        return liveDataCoinFavoritesListEntity
    }
}