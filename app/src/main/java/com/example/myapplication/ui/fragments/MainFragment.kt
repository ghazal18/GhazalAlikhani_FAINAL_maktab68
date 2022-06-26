package com.example.myapplication.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.adaptor.CategoriesAdaptor
import com.example.myapplication.adaptor.ProductAdaptor
import com.example.myapplication.adaptor.ViewPagerAdapter
import com.example.myapplication.data.Status
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.viewModels.CategoryViewModel
import com.example.myapplication.viewModels.MainProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewModel: MainProductViewModel by viewModels()
    val categoryViewModel: CategoryViewModel by viewModels()
    lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.statusLiveData.observe(viewLifecycleOwner) {
            if (it == Status.Loading){
                binding.animationLoading.visibility = View.VISIBLE
                binding.mainLinearLayout.visibility = View.GONE
            }
            else if(it == Status.Failed){
                binding.animationLoading.visibility = View.VISIBLE
                binding.mainLinearLayout.visibility = View.GONE
            }
            else{
                binding.animationLoading.visibility = View.GONE
                binding.mainLinearLayout.visibility = View.VISIBLE
            }
        }
        val adapter = ProductAdaptor() { Product ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(Product)
            findNavController().navigate(action)
        }
        val xadapter = ProductAdaptor() { Product ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(Product)
            findNavController().navigate(action)
        }
        val xxadapter = ProductAdaptor() { Product ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(Product)
            findNavController().navigate(action)
        }
        val categoryAdap = CategoriesAdaptor() {
            val action =
                MainFragmentDirections.actionMainFragmentToCategoryDetailsFragment(it.id.toString())
            findNavController().navigate(action)

        }
        binding.categoryRecyclerView.adapter = categoryAdap
        categoryViewModel.categoriesList.observe(viewLifecycleOwner) {
            println("loooooooooooooookkkkkkk hereee ${it.code} ${it.massage}" )
            if (it != null) {
                categoryAdap.submitList(it.data)
            }
        }

        binding.PopulartiyProductRecyclerView.adapter = adapter
        viewModel.productPopularityList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            }
        }
        binding.NewestProductRecyclerView.adapter = xxadapter
        viewModel.productDataList.observe(viewLifecycleOwner) {
            if (it != null) {
                xxadapter.submitList(it)
            }
        }
        binding.RatingProductRecyclerView.adapter = xadapter
        viewModel.productRatingList.observe(viewLifecycleOwner) {
            if (it != null) {
                xadapter.submitList(it)
            }
        }
        binding.showAllBest.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToShowAllFragment("best")
            findNavController().navigate(action)
        }
        binding.showAllNew.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToShowAllFragment("new")
            findNavController().navigate(action)
        }
        binding.showAllPopulary.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToShowAllFragment("popular")
            findNavController().navigate(action)
        }
        binding.showAllcategory.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_categoriesFragment)
        }
        viewModel.connectionStatus.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.sliderPhoto.observe(viewLifecycleOwner) {
            var list = it
            binding.viewPager.adapter = context?.let { ViewPagerAdapter(it, list) }
            binding.indicator.setViewPager2(binding.viewPager)
        }

    }
}