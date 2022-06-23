package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.AttributeTermItemViewBinding
import com.example.myapplication.model.Term


typealias AttributeTermClickHandler = (Term) -> Unit

class AttributeTermAdaptor(val onClick: AttributeTermClickHandler) :
    ListAdapter<Term, AttributeTermAdaptor.ItemHolder>(AttributeTermDiffCallback) {

    class ItemHolder(val binding: AttributeTermItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attribute: Term, onClick: AttributeTermClickHandler) {
            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                onClick.invoke(attribute)
            }
//            binding.checkBox.setOnClickListener {
//                onClick.invoke(attribute)
//            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: AttributeTermItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.attribute_term_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val attributeTerm = getItem(position)
        holder.binding.term = attributeTerm
        holder.bind(attributeTerm, onClick)
    }

}


object AttributeTermDiffCallback : DiffUtil.ItemCallback<Term>() {

    override fun areItemsTheSame(oldItem: Term, newItem: Term): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Term, newItem: Term): Boolean {
        return oldItem.id == newItem.id
    }
}

