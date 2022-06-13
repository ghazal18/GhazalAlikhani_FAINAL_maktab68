package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.OrderItemViewBinding
import com.example.myapplication.model.LineItem
import com.example.myapplication.model.ProductsItem

typealias OrderClickHandler = (LineItem) -> Unit

class OrderAdaptor(val onClick: OrderClickHandler) :
    ListAdapter<LineItem, OrderAdaptor.ItemHolder>(OrderDiffCallback) {

    class ItemHolder(val binding: OrderItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: LineItem, onClick: OrderClickHandler) {
            binding.linear.setOnClickListener {
                onClick.invoke(product)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: OrderItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.order_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val product = getItem(position)
        holder.binding.orderProduct = product
        holder.bind(product, onClick)
    }

}


object OrderDiffCallback : DiffUtil.ItemCallback<LineItem>() {

    override fun areItemsTheSame(oldItem: LineItem, newItem: LineItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LineItem, newItem: LineItem): Boolean {
        return oldItem.id == newItem.id
    }

}