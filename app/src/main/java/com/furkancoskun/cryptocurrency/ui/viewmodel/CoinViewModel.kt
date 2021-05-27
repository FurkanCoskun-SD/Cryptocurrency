package com.furkancoskun.cryptocurrency.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.furkancoskun.cryptocurrency.data.repository.CoinRepository
import com.furkancoskun.cryptocurrency.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CoinViewModel
@Inject
constructor(
    private val coinRepository: CoinRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getCoinMarkets(targetCurrency: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = coinRepository.getCoinMarkets(targetCurrency = targetCurrency)))
        } catch (e: KotlinNullPointerException) {
            emit(Resource.empty(data = null, exception = e))
        } catch (e: Exception) {
            emit(Resource.error(data = null, exception = e))
        }
    }

    fun getHistoricalPrice(id: String, targetCurrency: String, days: Int) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(
                    Resource.success(
                        data = coinRepository.getHistoricalPrice(
                            id = id,
                            targetCurrency = targetCurrency,
                            days = days
                        )
                    )
                )
            } catch (e: KotlinNullPointerException) {
                emit(Resource.empty(data = null, exception = e))
            } catch (e: Exception) {
                emit(Resource.error(data = null, exception = e))
            }
        }

}