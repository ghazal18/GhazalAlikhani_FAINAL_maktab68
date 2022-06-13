package com.example.myapplication.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ViewPageHolderBinding
import com.example.myapplication.model.Image

class ViewPagerAdapter(
    private val context: Context,
    private val labelList: List<Image>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding =
            ViewPageHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val label = labelList[position]
        holder.bind(label)
    }

    override fun getItemCount(): Int {
        return labelList.size
    }

    class ViewPagerHolder(private var itemHolderBinding: ViewPageHolderBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {
        fun bind(label: Image) {
            Glide.with(itemView).load(label.src).into(itemHolderBinding.labelHeader)
        }
    }
}
