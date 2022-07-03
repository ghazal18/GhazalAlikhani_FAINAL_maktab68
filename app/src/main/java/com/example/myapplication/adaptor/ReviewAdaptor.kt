package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ReviewListItemViewBinding
import com.example.myapplication.model.ReviewsItem
import com.example.myapplication.RemoveTag

typealias ReviewClickHandler = (ReviewsItem) -> Unit

class ReviewAdaptor(val onClick: ReviewClickHandler, val onClick2: ReviewClickHandler) :
    ListAdapter<ReviewsItem, ReviewAdaptor.ItemHolder>(ReviewsDiffCallback) {

    class ItemHolder(val binding: ReviewListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewsItem, onClick: ReviewClickHandler, onClick2: ReviewClickHandler) {
            binding.textViewReview.text = RemoveTag.removingTag(review.review)
            try {
                Glide.with(itemView).load(review.reviewer_avatar_urls.`48`)
                    .into(binding.imageViewAvatar)
            } catch (e: Exception) {
                binding.imageViewAvatar.setImageResource(R.drawable.ic_connection_error)
            }
            var numberOfStars = review.rating.toFloat()
            binding.rBar.rating = numberOfStars
            binding.deleteCommentButton.setOnClickListener {
                onClick.invoke(review)
            }
            binding.editCommentButton.setOnClickListener {
                onClick2.invoke(review)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding: ReviewListItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.review_list_item_view,
            parent, false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val reviews = getItem(position)
        holder.binding.reviews = reviews
        holder.bind(reviews, onClick, onClick2)
    }

}


object ReviewsDiffCallback : DiffUtil.ItemCallback<ReviewsItem>() {

    override fun areItemsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem): Boolean {
        return oldItem.id == newItem.id
    }

}