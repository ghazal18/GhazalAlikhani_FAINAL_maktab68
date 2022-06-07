package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ProductShowAllItemViewBinding
import com.example.myapplication.model.ProductsItem

typealias ProductShowAllClickHandler = (ProductsItem) -> Unit
class ListOfProductAdaptor(val onClick: ProductShowAllClickHandler)
    : ListAdapter<ProductsItem, ListOfProductAdaptor.ItemHolder>(ProductDiffCallback) {

    class ItemHolder(val binding: ProductShowAllItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductsItem, onClick: ProductShowAllClickHandler) {
            Glide.with(itemView).load(product.images[0].src).into(binding.productImageView)
            var numberOfStars = product.average_rating.toFloat()
            binding.rBar.rating = numberOfStars
            binding.linear.setOnClickListener {
                onClick.invoke(product)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ProductShowAllItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_show_all_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val product = getItem(position)
        holder.binding.productt = product
        holder.bind(product, onClick)
    }

}


object ProductDiffCallback : DiffUtil.ItemCallback<ProductsItem>() {

    override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem.id == newItem.id
    }

}