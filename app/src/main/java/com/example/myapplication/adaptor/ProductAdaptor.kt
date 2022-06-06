package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ProductListItemViewBinding
import com.example.myapplication.model.ProductsItem
import com.bumptech.glide.Glide

typealias ProductClickHandler = (ProductsItem) -> Unit

class ProductAdaptor(val onClick: ProductClickHandler)
    : ListAdapter<ProductsItem, ProductAdaptor.ItemHolder>(MovieDiffCallback) {

    class ItemHolder(val binding: ProductListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductsItem, onClick: ProductClickHandler) {
           Glide.with(itemView).load(product.images[0].src).into(binding.productImage)
            binding.linear.setOnClickListener {
                onClick.invoke(product)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ProductListItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_list_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val product = getItem(position)
        holder.binding.product = product
        holder.bind(product, onClick)
    }

}


object MovieDiffCallback : DiffUtil.ItemCallback<ProductsItem>() {

    override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem.id == newItem.id
    }

}