package com.furkancoskun.cryptocurrency.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.data.room.CoinFavoritesListEntity
import com.furkancoskun.cryptocurrency.databinding.FragmentFavoritesBinding
import com.furkancoskun.cryptocurrency.ui.adapter.FavoritesAdapter
import com.furkancoskun.cryptocurrency.ui.base.BaseFragment
import com.furkancoskun.cryptocurrency.ui.viewmodel.DBViewModel

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites), FavoritesAdapter.Interaction {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val dbViewModel: DBViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        initRecyclerView()

        getFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorites.adapter = null
        _binding = null
    }

    private fun setupUI() {
        binding.customAppBar.tvPageTitle.text = getString(R.string.TitleFavorites)
    }

    private fun initRecyclerView() {
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context)

            favoritesAdapter = FavoritesAdapter(
                interaction = this@FavoritesFragment
            )

            adapter = favoritesAdapter
        }
    }

    override fun onItemClick(position: Int, item: CoinFavoritesListEntity) {
    }

    private fun getFavorites() {
        dbViewModel.getFavorites(requireContext())!!.observe(requireActivity(), Observer {
            if (it != null) {
                favoritesAdapter.submitList(it)
            }
        })
    }
}