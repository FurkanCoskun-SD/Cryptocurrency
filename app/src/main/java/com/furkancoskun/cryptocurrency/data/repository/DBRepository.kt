package com.furkancoskun.cryptocurrency.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.furkancoskun.cryptocurrency.data.room.CoinFavoritesListEntity
import com.furkancoskun.cryptocurrency.data.room.CoinsDatabase
import com.furkancoskun.cryptocurrency.data.room.CoinsListEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DBRepository {
    companion object {

        var coinsDatabase: CoinsDatabase? = null
        var coinsListEntity: LiveData<List<CoinsListEntity>>? = null
        var coinFavoritesListEntity: LiveData<List<CoinFavoritesListEntity>>? = null

        fun initializeDB(context: Context) : CoinsDatabase {
            return CoinsDatabase.getDatabaseClient(context)
        }

        fun addCoinMarkets(context: Context, coinsListEntity : List<CoinsListEntity>) {
            coinsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                coinsDatabase!!.coinsListDao().addCoinMarkets(coinsListEntity)
            }
        }

        fun getCoinsMarkets(context: Context) : LiveData<List<CoinsListEntity>>? {
            coinsDatabase = initializeDB(context)
            coinsListEntity = coinsDatabase!!.coinsListDao().getCoinsMarkets()
            return coinsListEntity
        }

        fun searchCoinsMarkets(context: Context, symbol: String) : LiveData<List<CoinsListEntity>>? {
            coinsDatabase = initializeDB(context)
            coinsListEntity = coinsDatabase!!.coinsListDao().serachCoinsMarkets(symbol)
            return coinsListEntity
        }

        fun addFavorites(context: Context, coinsListEntity : List<CoinFavoritesListEntity>) {
            coinsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                coinsDatabase!!.coinsListDao().addFavorites(coinsListEntity)
            }
        }

        fun getFavorites(context: Context) : LiveData<List<CoinFavoritesListEntity>>? {
            coinsDatabase = initializeDB(context)
            coinFavoritesListEntity = coinsDatabase!!.coinsListDao().getFavorites()
            return coinFavoritesListEntity
        }

    }

}