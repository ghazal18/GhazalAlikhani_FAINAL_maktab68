package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import com.example.myapplication.R
import com.example.myapplication.adaptor.CategoriesAdaptor
import com.example.myapplication.adaptor.ProductAdaptor
import com.example.myapplication.adaptor.ViewPagerAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.network.Resource
import com.example.myapplication.viewModels.CategoryViewModel
import com.example.myapplication.viewModels.MainProductViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    val viewModel: MainProductViewModel by viewModels()
    val categoryViewModel: CategoryViewModel by viewModels()
    lateinit var binding: FragmentMainBinding
    val handler = Handler()
    var origPosition: Int = 0
    private val timerDelay: Long = 10 * 1000

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


    @SuppressLint("ShowToast", "DetachAndAttachSameFragment")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = ProductAdaptor("پربازدید محصولات") { Product ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(Product)
            findNavController().navigate(action)
        }
        val xadapter = ProductAdaptor("بهترین محصولات") { Product ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(Product)
            findNavController().navigate(action)
        }
        val xxadapter = ProductAdaptor("جدیدترین محصولات") { Product ->
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
            if (it != null) {
                categoryAdap.submitList(it.data)
            }
        }

        binding.PopulartiyProductRecyclerView.adapter = adapter
        viewModel.productPopularityList.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it.data)
            }
        }
        binding.NewestProductRecyclerView.adapter = xxadapter
        viewModel.productDataList.observe(viewLifecycleOwner) {
            if (it != null) {
                xxadapter.submitList(it.data)
            }
        }
        binding.RatingProductRecyclerView.adapter = xadapter
        viewModel.productRatingList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.animationLoading.visibility = View.VISIBLE
                    binding.mainLinearLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.animationLoading.visibility = View.GONE
                    binding.mainLinearLayout.visibility = View.VISIBLE
                    xadapter.submitList(it.data)
                }
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

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val runnable = Runnable {  binding.viewPager.setCurrentItem(position + 1) }
                if (position <  binding.viewPager.adapter?.itemCount ?: 0) {
                    handler.postDelayed(runnable, timerDelay)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if (state == SCROLL_STATE_DRAGGING) handler.removeMessages(0)
            }
        })

        viewModel.connectionStatus.observe(viewLifecycleOwner) { connection ->
            if (!connection) {
                val snackbar = Snackbar.make(
                    binding.mainLinearLayouttt,
                    "No Internet Connection",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("reload", View.OnClickListener {
                        println("reload button clicked")
                        viewModel.getPopularProducts()
                        viewModel.getRatingProducts()
                        viewModel.getDatingProducts()
                        viewModel.getPopularProducts()
                        viewModel.slider()
                        categoryViewModel.getCategories()



                    }).show()
            }
        }
        viewModel.sliderPhoto.observe(viewLifecycleOwner) {
            var list = it
            binding.viewPager.adapter = context?.let {
                list.data?.get(0)
                    ?.let { it1 -> ViewPagerAdapter(it, it1.images) }
            }
            binding.indicator.setViewPager2(binding.viewPager)
        }

    }
}