package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.CategoryListItemViewBinding
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem


typealias CategoriesClickHandler = (CategoriesItem) -> Unit

class CategoriesAdaptor(val onClick: CategoriesClickHandler)
    : ListAdapter<CategoriesItem, CategoriesAdaptor.ItemHolder>(CategoriesDiffCallback) {

    class ItemHolder(val binding: CategoryListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoriesItem, onClick: CategoriesClickHandler) {
            Glide.with(itemView).load(category.image.src).into(binding.categoryImage)
            binding.linear.setOnClickListener {
                onClick.invoke(category)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: CategoryListItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_list_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val category = getItem(position)
        holder.binding.category = category
        holder.bind(category, onClick)
    }

}


object CategoriesDiffCallback : DiffUtil.ItemCallback<CategoriesItem>() {

    override fun areItemsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
        return oldItem.id == newItem.id
    }

}