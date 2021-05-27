package com.furkancoskun.cryptocurrency.ui.coinMarkets

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.data.model.common.AlertStatusType
import com.furkancoskun.cryptocurrency.data.room.CoinFavoritesListEntity
import com.furkancoskun.cryptocurrency.data.room.CoinsListEntity
import com.furkancoskun.cryptocurrency.databinding.FragmentCoinMarketsBinding
import com.furkancoskun.cryptocurrency.ext.gone
import com.furkancoskun.cryptocurrency.ext.navigate
import com.furkancoskun.cryptocurrency.ext.showError
import com.furkancoskun.cryptocurrency.ext.visible
import com.furkancoskun.cryptocurrency.ui.adapter.CoinMarketAdapter
import com.furkancoskun.cryptocurrency.ui.base.BaseFragment
import com.furkancoskun.cryptocurrency.ui.viewmodel.CoinViewModel
import com.furkancoskun.cryptocurrency.ui.viewmodel.DBViewModel
import com.furkancoskun.cryptocurrency.utils.Status
import com.google.gson.Gson

class CoinMarketsFragment : BaseFragment(R.layout.fragment_coin_markets),
    CoinMarketAdapter.Interaction {

    private var _binding: FragmentCoinMarketsBinding? = null
    private val binding get() = _binding!!

    private val coinViewModel: CoinViewModel by viewModels()
    private val dbViewModel: DBViewModel by viewModels()
    private lateinit var coinMarketAdapter: CoinMarketAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinMarketsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        handleClickListeners()
        initRecyclerView()

        getCoinMarkets("usd")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvCoinMarket.adapter = null
        _binding = null
    }

    private fun setupUI() {
        binding.tvPageTitle.text = "Madeni Paralar"
    }

    private fun handleClickListeners() {
        // Back
        /*binding.customAppBar.rlBack.setOnClickListener {
            findNavController().popBackStack()
        }*/

        binding.rlSearch.setOnClickListener {
            binding.rlSearch.gone()
            binding.tvPageTitle.gone()
            binding.searchEvent.visible()
            binding.searchEvent.isIconified = false
        }

        binding.searchEvent.setOnCloseListener {
            binding.searchEvent.gone()
            binding.tvPageTitle.visible()
            binding.rlSearch.visible()
            getEntity()
            false
        }

        binding.searchEvent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchEvent.clearFocus()
                query?.let {
                    getSearchEntity(it)
                }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    getSearchEntity(it)
                }

                return false
            }
        })

        binding.rvCoinMarket.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val topRowVerticalPosition =
                    if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(
                        0
                    ).top
                binding.swipe.isEnabled = topRowVerticalPosition >= 0
            }
        })

        binding.swipe.setOnRefreshListener {
            Handler().postDelayed({
                getCoinMarkets("usd")
                binding.swipe.isRefreshing = false
            }, 1000)
        }
    }

    private fun initRecyclerView() {
        binding.rvCoinMarket.apply {
            layoutManager = LinearLayoutManager(context)

            coinMarketAdapter = CoinMarketAdapter(
                interaction = this@CoinMarketsFragment
            )

            adapter = coinMarketAdapter
        }
    }

    private fun getCoinMarkets(targetCurrency: String) {
        coinViewModel.getCoinMarkets(
            targetCurrency = targetCurrency
        ).observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    dataStateChangeListener.showProgressBar(false)
                    resource.data?.let {
                        val coinsListEntity = ArrayList<CoinsListEntity>()

                        for (item in it) {
                            coinsListEntity.add(
                                CoinsListEntity(
                                    symbol = item.symbol,
                                    id = item.id,
                                    name = item.name,
                                    price = item.price,
                                    changePercent = item.changePercent,
                                    image = item.image
                                )
                            )
                        }

                        dbViewModel.addCoinMarkets(requireContext(), coinsListEntity)

                        getEntity()
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

    override fun onItemClick(position: Int, item: CoinsListEntity) {
        navigate(CoinMarketsFragmentDirections.actionCoinMarketsFragmentToCoinMarketFragment(
            Gson().toJson(item)
        ))
    }

    private fun getEntity() {
        dbViewModel.getCoinsMarkets(requireContext())!!.observe(requireActivity(), Observer {
            if (it != null) {
                coinMarketAdapter.submitList(it)
            }
        })
    }

    private fun getSearchEntity(symbol: String) {
        dbViewModel.searchCoinsMarkets(context = requireContext(), symbol = symbol)!!.observe(
            requireActivity(),
            Observer {
                if (it != null) {
                    coinMarketAdapter.submitList(it)
                }
            })
    }
}