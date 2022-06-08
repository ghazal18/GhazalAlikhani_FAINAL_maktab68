package com.example.myapplication.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.adaptor.ViewPagerAdapter
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.myapplication.model.ProductsItem
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator3
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding
    val viewModel: MainProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.indicator.setViewPager(binding.viewPager)
        viewModel.connectionStatus.observe(viewLifecycleOwner){
            if (!it){
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
    }


}