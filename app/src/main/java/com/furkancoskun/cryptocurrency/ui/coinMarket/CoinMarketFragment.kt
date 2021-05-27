package com.furkancoskun.cryptocurrency.ui.coinMarket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.data.model.common.AlertStatusType
import com.furkancoskun.cryptocurrency.data.room.CoinFavoritesListEntity
import com.furkancoskun.cryptocurrency.data.room.CoinsListEntity
import com.furkancoskun.cryptocurrency.databinding.FragmentCoinMarketBinding
import com.furkancoskun.cryptocurrency.ext.priceFormat
import com.furkancoskun.cryptocurrency.ext.showError
import com.furkancoskun.cryptocurrency.ui.base.BaseFragment
import com.furkancoskun.cryptocurrency.ui.viewmodel.CoinViewModel
import com.furkancoskun.cryptocurrency.ui.viewmodel.DBViewModel
import com.furkancoskun.cryptocurrency.utils.ChartHelper
import com.furkancoskun.cryptocurrency.utils.Status
import com.furkancoskun.cryptocurrency.utils.UIHelper
import com.google.gson.Gson

class CoinMarketFragment : BaseFragment(R.layout.fragment_coin_market) {

    private var _binding: FragmentCoinMarketBinding? = null
    private val binding get() = _binding!!

    private val args: CoinMarketFragmentArgs by navArgs()
    private val coinViewModel: CoinViewModel by viewModels()
    private val dbViewModel: DBViewModel by viewModels()

    private lateinit var mCoinsListEntity: CoinsListEntity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinMarketBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCoinsListEntity = Gson().fromJson(args.coinsListEntity, CoinsListEntity::class.java)

        setupUI()
        handleClickListeners()

        getHistoricalPrice(mCoinsListEntity.id ,"usd", 30)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.customAppBar.tvPageTitle.text = mCoinsListEntity.name
        binding.customAppBar.ivBack.setImageResource(R.drawable.ic_back)
        binding.customAppBar.ivRightView.setImageResource(R.drawable.ic_heart)

        Glide.with(binding.root).load(mCoinsListEntity.image).into(binding.ivCoinMarket)
        binding.tvCoinMarket.text = mCoinsListEntity.name
        binding.tvSymbol.text = mCoinsListEntity.symbol
        binding.tvCurrentPrice.text = mCoinsListEntity.price.priceFormat()

        UIHelper.showChangePercent(binding.tvPriceChangeRate, mCoinsListEntity.changePercent)
    }

    private fun handleClickListeners() {
        // Back
        binding.customAppBar.rlBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.customAppBar.rlRightView.setOnClickListener {
            dataStateChangeListener.showAlert(
                AlertStatusType.SUCCESS.code,
                "Favorilere eklendi",
                true
            )


            val coinFavoritesListEntity = ArrayList<CoinFavoritesListEntity>()
            coinFavoritesListEntity.add(
                CoinFavoritesListEntity(
                    symbol = mCoinsListEntity.symbol,
                    id = mCoinsListEntity.id,
                    name = mCoinsListEntity.name,
                    price = mCoinsListEntity.price,
                    changePercent = mCoinsListEntity.changePercent,
                    image = mCoinsListEntity.image
                )
            )
            dbViewModel.addFavorites(requireContext(), coinFavoritesListEntity)
        }
    }

    private fun getHistoricalPrice(id: String ,targetCurrency: String, days: Int) {
        coinViewModel.getHistoricalPrice(
            id = id,
            targetCurrency = targetCurrency,
            days = days
            ).observe(viewLifecycleOwner, { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dataStateChangeListener.showProgressBar(false)
                        resource.data?.let {
                            ChartHelper.displayHistoricalLineChart(binding.lineChart, mCoinsListEntity.symbol, it.prices)
                        }
                    }
                    Status.ERROR -> {
                        dataStateChangeListener.showProgressBar(false)
                        resource.exception?.let {
                            dataStateChangeListener.showAlert(
                                AlertStatusType.ERROR.code,
                                it.showError(context)
                            )
                        }
                    }
                    Status.EMPTY -> {
                        dataStateChangeListener.showProgressBar(false)
                    }
                    Status.LOADING -> {
                        dataStateChangeListener.showProgressBar(true)
                    }
                }
            })

    }

}