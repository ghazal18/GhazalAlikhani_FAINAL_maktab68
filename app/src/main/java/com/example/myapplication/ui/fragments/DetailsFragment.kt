package com.example.myapplication.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.adaptor.ReviewAdaptor
import com.example.myapplication.adaptor.ViewPagerAdapter
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.myapplication.data.ArrayOfProductDetails
import com.example.myapplication.viewModels.MainProductViewModel
import com.example.myapplication.RemoveTag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding
    val viewModel: MainProductViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var clicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = this.requireActivity().getSharedPreferences("order", Context.MODE_PRIVATE)
        editor = sp.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var porductt = args.productsss
        binding.product = porductt
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.cardView.setBackgroundResource(R.drawable.rounded_top_corner)
        binding.viewPager.adapter = context?.let { ViewPagerAdapter(it, porductt.images) }
        binding.indicator.setViewPager2(binding.viewPager)
        var numberOfStars = porductt.average_rating.toFloat()
        binding.rBar.rating = numberOfStars
        viewModel.connectionStatus.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
        binding.textViewProductDis.text = RemoveTag.removingTag(porductt.description)

        binding.textView.text = viewModel.number.toString()

        viewModel.number.observe(viewLifecycleOwner) {
            binding.textViewNumber.text = it.toString()
        }
        binding.buttonDec.setOnClickListener {
            viewModel.decTheNumber()
        }
        binding.buttonInc.setOnClickListener {
            viewModel.incTheNumber()
        }
        binding.buyButton.setOnClickListener {
            if (!clicked) {
                ArrayOfProductDetails.idOfProductArray.add(porductt.id)
                val separator = "-"
                val string = ArrayOfProductDetails.idOfProductArray.joinToString(separator)
                println("the id: " + string)
                editor.putString("id", string)
                ArrayOfProductDetails.numberOfProductArray.add(
                    binding.textViewNumber.text.toString().toInt()
                )
                val string2 = ArrayOfProductDetails.numberOfProductArray.joinToString(separator)
                println("the count: " + string2)
                editor.putString("count", string2)
                editor.apply()
                clicked = true
            }
        }
        viewModel.getReviews(porductt.id)

        binding.reviewsButton.setOnClickListener {
            binding.linearLayoutReviews.visibility = View.VISIBLE
            binding.linearLayoutSpecifications.visibility = View.GONE
        }
        binding.SpecificationsButton.setOnClickListener {
            binding.linearLayoutReviews.visibility = View.GONE
            binding.linearLayoutSpecifications.visibility = View.VISIBLE
        }
        val reviewAdaptor = ReviewAdaptor {

        }
        binding.reviewRecyclerView.adapter = reviewAdaptor
        viewModel.reviewsList.observe(viewLifecycleOwner) {
            reviewAdaptor.submitList(it.data)
        }
        binding.imageViewAddComment.setOnClickListener{

        }
    }


}