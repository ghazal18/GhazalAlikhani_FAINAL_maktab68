package com.example.myapplication.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.ProductRemoteDataSource
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.databinding.OrderItemViewBinding
import com.example.myapplication.model.LineItem
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.network.ApiService
import com.example.myapplication.ui.MainProductViewModel
import com.example.myapplication.ui.OrderViewModel
import com.example.myapplication.ui.srcOfProductItem
import io.reactivex.annotations.NonNull

//typealias OrderClickHandler = (LineItem) -> Unit

//class OrderAdaptor(val onClick: OrderClickHandler,val viewModel: OrderViewModel) :
class OrderAdaptor(val viewModel: OrderViewModel, val src: String) :
    ListAdapter<LineItem, OrderAdaptor.ItemHolder>(OrderDiffCallback) {
    class ItemHolder(val binding: OrderItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // fun bind(orderItem: LineItem, onClick: OrderClickHandler,viewModel: OrderViewModel) {
        fun bind(orderItem: LineItem, viewModel: OrderViewModel, src: String) {
            viewModel.searchForProductWithId(orderItem.product_id)


            val imageUrl = srcOfProductItem
            try {
                Glide.with(itemView).load(imageUrl).into(binding.orderImageView)

            } catch (e: Exception) {
                binding.orderImageView.setImageResource(R.drawable.ic_connection_error)
            }
//            binding.linear.setOnClickListener {
//                onClick.invoke(orderItem)
//            }
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
        //    holder.bind(product, onClick,viewModel)
        holder.bind(product, viewModel, src)
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
