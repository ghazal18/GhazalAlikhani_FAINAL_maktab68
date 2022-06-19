package com.example.myapplication.adaptor

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.AttributeListItemViewBinding
import com.example.myapplication.model.AttributesItem


typealias AttributeClickHandler = (AttributesItem) -> Unit

class AttributeAdaptor(val onClick: AttributeClickHandler) :
    ListAdapter<AttributesItem, AttributeAdaptor.ItemHolder>(AttributeDiffCallback) {

    class ItemHolder(val binding: AttributeListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attribute: AttributesItem, onClick: AttributeClickHandler) {

            binding.attributeTextView.setOnClickListener {
                onClick.invoke(attribute)

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: AttributeListItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.attribute_list_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val attribute = getItem(position)
        holder.binding.attribute = attribute
        holder.bind(attribute, onClick)
    }

}


object AttributeDiffCallback : DiffUtil.ItemCallback<AttributesItem>() {

    override fun areItemsTheSame(oldItem: AttributesItem, newItem: AttributesItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AttributesItem, newItem: AttributesItem): Boolean {
        return oldItem.id == newItem.id
    }

}