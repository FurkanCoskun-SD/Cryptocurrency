package com.furkancoskun.cryptocurrency.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkancoskun.cryptocurrency.data.room.DB.DATABASE_NAME
import com.furkancoskun.cryptocurrency.data.room.DB.DATABASE_VERSION

@Database(entities = [CoinsListEntity::class, CoinFavoritesListEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class CoinsDatabase : RoomDatabase() {

    abstract fun coinsListDao(): CoinsListDao

    companion object {

        @Volatile
        private var INSTANCE: CoinsDatabase? = null

        fun getDatabaseClient(context: Context): CoinsDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, CoinsDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }

    }
}