package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.ArrayOfProductDetails
import com.example.myapplication.databinding.ProductOrderItemViewBinding
import com.example.myapplication.databinding.ProductShowAllItemViewBinding
import com.example.myapplication.model.ProductsItem


typealias OrderProductShowAllClickHandler = (Int) -> Unit

class OrderProductAdaptor(
    val QuantityList: List<Int>,
    val onClick: OrderProductShowAllClickHandler,
    val onClick2: OrderProductShowAllClickHandler
) :
    ListAdapter<ProductsItem, OrderProductAdaptor.ItemHolder>(OrderProductDiffCallback) {

    inner class ItemHolder(val binding: ProductOrderItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: ProductsItem,
            onClick: OrderProductShowAllClickHandler,
            position: Int,
            onClick2: OrderProductShowAllClickHandler
        ) {
            try {
                Glide.with(itemView).load(product.images[0].src).into(binding.imageViewProduct)
            } catch (e: Exception) {
                binding.imageViewProduct.setImageResource(R.drawable.ic_connection_error)
            }
            binding.textViewQuantity.text = QuantityList[position].toString()
            binding.buttonDecrease.setOnClickListener {
                var quantity = binding.textViewQuantity.text.toString().toInt()
                var increase = ++quantity
                binding.textViewQuantity.text = increase.toString()
                onClick.invoke(position)
            }
            binding.buttonIncrease.setOnClickListener {
                var quantity = binding.textViewQuantity.text.toString().toInt()
                var decrease = --quantity
                binding.textViewQuantity.text = decrease.toString()
                onClick2.invoke(position)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderProductAdaptor.ItemHolder {
        val binding: ProductOrderItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_order_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val product = getItem(position)

        holder.binding.orderProduct = product
        holder.bind(product, onClick, position, onClick2)
    }

}


object OrderProductDiffCallback : DiffUtil.ItemCallback<ProductsItem>() {

    override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem.id == newItem.id
    }

}