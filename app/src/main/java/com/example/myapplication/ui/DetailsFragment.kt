package com.example.myapplication.ui

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
import com.example.myapplication.adaptor.ViewPagerAdapter
import com.example.myapplication.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding
    val viewModel: MainProductViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
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
            ArrayOfProductDetails.idOfProductArray.add(porductt.id)
            val separator = "-"
            val string = ArrayOfProductDetails.idOfProductArray.joinToString(separator)
            println(string)
            editor.putString("id", string)
            editor.apply()
        }

    }


}