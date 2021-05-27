package com.furkancoskun.cryptocurrency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkancoskun.cryptocurrency.data.room.CoinFavoritesListEntity
import com.furkancoskun.cryptocurrency.databinding.ItemCoinMarketBinding
import com.furkancoskun.cryptocurrency.ext.gone
import com.furkancoskun.cryptocurrency.ext.priceFormat

class FavoritesAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CoinFavoritesListEntity>() {

        override fun areItemsTheSame(
            oldItem: CoinFavoritesListEntity,
            newItem: CoinFavoritesListEntity
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: CoinFavoritesListEntity,
            newItem: CoinFavoritesListEntity
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCoinMarketBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CoinMarketViewHolder(binding, interaction)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CoinFavoritesListEntity>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoinMarketViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    class CoinMarketViewHolder
    constructor(
        val binding: ItemCoinMarketBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinFavoritesListEntity) {

            binding.root.setOnClickListener {
                interaction?.onItemClick(bindingAdapterPosition, item)
            }

            Glide.with(binding.root).load(item.image).into(binding.ivCoinMarket)

            binding.tvCoinMarket.text = item.name
            binding.tvSymbol.text = item.symbol

            binding.tvCurrentPrice.gone()
            binding.tvPriceChangeRate.gone()
        }
    }

    interface Interaction {
        fun onItemClick(position: Int, item: CoinFavoritesListEntity)
    }

}