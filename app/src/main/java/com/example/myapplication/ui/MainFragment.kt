package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adaptor.CategoriesAdaptor
import com.example.myapplication.adaptor.ProductAdaptor
import com.example.myapplication.databinding.FragmentMainBinding
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
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
        val categoryAdap = CategoriesAdaptor(){


        }
        binding.categoryRecyclerView.adapter = categoryAdap
        categoryViewModel.categorieslist.observe(viewLifecycleOwner){
            if (it != null){
                categoryAdap.submitList(it)
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
        binding.textViewCategory.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_categoriesFragment)
        }

    }
}