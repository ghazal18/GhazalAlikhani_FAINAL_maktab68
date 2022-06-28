package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.OrderItemViewBinding
import com.example.myapplication.model.LineItem
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.viewModels.OrderViewModel


typealias OrderClickHandler = (LineItem) -> Unit

class OrderAdaptor(val onClick: OrderClickHandler, val onClick2: OrderClickHandler) :
    ListAdapter<LineItem, OrderAdaptor.ItemHolder>(OrderDiffCallback) {

    class ItemHolder(val binding: OrderItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderProduct: LineItem, onClick: OrderClickHandler, onClick2: OrderClickHandler) {
            Glide.with(itemView).load(orderProduct.image.src).into(binding.imageViewProduct)
            binding.buttonDecrease.setOnClickListener {
                var quantity = binding.textViewQuantity.text.toString().toInt()
                var increase = ++quantity
                binding.textViewQuantity.text = increase.toString()
                onClick.invoke(orderProduct)
            }
            binding.buttonIncrease.setOnClickListener {
                var quantity = binding.textViewQuantity.text.toString().toInt()
                var decrease = --quantity
                binding.textViewQuantity.text = decrease.toString()
                onClick2.invoke(orderProduct)
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
        val order = getItem(position)
        holder.binding.orderProduct = order
        holder.bind(order, onClick, onClick2)
    }

}


object OrderDiffCallback : DiffUtil.ItemCallback<LineItem>() {

    override fun areItemsTheSame(oldItem: LineItem, newItem: LineItem): Boolean {
        return oldItem.product_id == newItem.product_id
    }

    override fun areContentsTheSame(oldItem: LineItem, newItem: LineItem): Boolean {
        return oldItem.product_id == newItem.product_id
    }

}
