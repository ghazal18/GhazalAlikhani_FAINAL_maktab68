package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ProductRelatedItemViewBinding
import com.example.myapplication.model.ProductsItem

typealias ProductRelatedClickHandler = (ProductsItem) -> Unit

class ProductRelatedAdaptor(val onClick: ProductRelatedClickHandler) :
    ListAdapter<ProductsItem, ProductRelatedAdaptor.ItemHolder>(ProductRelatedDiffCallback) {

    class ItemHolder(val binding: ProductRelatedItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderProduct: ProductsItem, onClick: ProductRelatedClickHandler) {
            Glide.with(itemView).load(orderProduct.images[0].src).into(binding.productImage)
            binding.linear.setOnClickListener {
                onClick.invoke(orderProduct)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ProductRelatedItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_related_item_view,
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


object ProductRelatedDiffCallback : DiffUtil.ItemCallback<ProductsItem>() {

    override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem.id == newItem.id
    }


}
