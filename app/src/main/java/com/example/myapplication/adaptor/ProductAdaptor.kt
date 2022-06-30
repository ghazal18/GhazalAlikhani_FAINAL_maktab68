package com.example.myapplication.adaptor

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ProductListItemViewBinding
import com.example.myapplication.model.ProductsItem
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.HeaderProductBinding

typealias ProductClickHandler = (ProductsItem) -> Unit

class ProductAdaptor(val onClick: ProductClickHandler) :
    ListAdapter<ProductsItem, Holder<*>>(MovieDiffCallback) {

    class ItemHolder(val binding1: ProductListItemViewBinding) :
        Holder<HeaderProductBinding>(binding1) {

        override fun bind(product: ProductsItem, onClick: ProductClickHandler) {
            binding1.textViewProductName.text = product.name
            binding1.textViewPrice.text = product.price
            try {
                Glide.with(itemView).load(product.images[0].src).into(binding1.productImage)
            } catch (e: Exception) {
                binding1.productImage.setImageResource(R.drawable.ic_connection_error)
            }
            binding1.linear.setOnClickListener {
                onClick.invoke(product)
            }
        }
    }

    class HeaderHolder(val binding2: HeaderProductBinding) :
        Holder<HeaderProductBinding>(binding2) {
        override fun bind(product: ProductsItem, onClick: ProductClickHandler) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<*> {
        return if (viewType == 0) {
            val binding: HeaderProductBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.header_product,
                parent, false
            )
            HeaderHolder(binding)
        } else {
            val binding: ProductListItemViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_list_item_view,
                parent, false
            )
            ItemHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0 ){
            return 0
        }else{
            return 1
        }
    }

    override fun onBindViewHolder(holder: Holder<*>, position: Int) {
        val product = getItem(position)
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

abstract class Holder<T>(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(product: ProductsItem, onClick: ProductClickHandler)
}