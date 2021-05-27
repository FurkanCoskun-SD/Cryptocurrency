package com.furkancoskun.cryptocurrency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furkancoskun.cryptocurrency.data.room.CoinsListEntity
import com.furkancoskun.cryptocurrency.databinding.ItemCoinMarketBinding
import com.furkancoskun.cryptocurrency.ext.priceFormat
import com.furkancoskun.cryptocurrency.utils.UIHelper

class CoinMarketAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CoinsListEntity>() {

        override fun areItemsTheSame(
            oldItem: CoinsListEntity,
            newItem: CoinsListEntity
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: CoinsListEntity,
            newItem: CoinsListEntity
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

    fun submitList(list: List<CoinsListEntity>) {
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

        fun bind(item: CoinsListEntity) {

            binding.root.setOnClickListener {
                interaction?.onItemClick(bindingAdapterPosition, item)
            }

            Glide.with(binding.root).load(item.image).into(binding.ivCoinMarket)

            binding.tvCoinMarket.text = item.name
            binding.tvSymbol.text = item.symbol
            binding.tvCurrentPrice.text = item.price!!.priceFormat()
            binding.tvPriceChangeRate.text = item.changePercent.toString()

            UIHelper.showChangePercent(binding.tvPriceChangeRate, item.changePercent)
        }
    }

    interface Interaction {
        fun onItemClick(position: Int, item: CoinsListEntity)
    }

}