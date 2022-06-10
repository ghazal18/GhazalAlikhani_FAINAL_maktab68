package com.example.myapplication.adaptor


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ListOfCategoriesItemViewBinding
import com.example.myapplication.databinding.ProductShowAllItemViewBinding
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem

typealias CategoriesShowAllClickHandler = (CategoriesItem) -> Unit

class ListOfCategoriesAdaptor(val onClick: CategoriesShowAllClickHandler) :
    ListAdapter<CategoriesItem, ListOfCategoriesAdaptor.ItemHolder>(CategoryListDiffCallback) {

    class ItemHolder(val binding: ListOfCategoriesItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoriesItem, onClick: CategoriesShowAllClickHandler) {
            try {
                Glide.with(itemView).load(category.image.src).into(binding.categoryImageInList)
            } catch (e: Exception) {
                binding.categoryImageInList.setImageResource(R.drawable.ic_connection_error)
            }
            binding.linear.setOnClickListener {
                onClick.invoke(category)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ListOfCategoriesItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_of_categories_item_view,
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


object CategoryListDiffCallback : DiffUtil.ItemCallback<CategoriesItem>() {

    override fun areItemsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
        return oldItem.id == newItem.id
    }

}