package com.furkancoskun.cryptocurrency.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins_list")
data class CoinsListEntity(
    @PrimaryKey val symbol: String,
    val id: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
    val image: String
)

@Entity(tableName = "coins_favorites")
data class CoinFavoritesListEntity(
    @PrimaryKey val symbol: String,
    val id: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
    val image: String
)