package com.example.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.adaptor.AttributeAdaptor
import com.example.myapplication.adaptor.AttributeTermAdaptor
import com.example.myapplication.databinding.FragmentFilterBinding
import com.example.myapplication.data.ArrayOfProductDetails
import com.example.myapplication.viewModels.SearchProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    lateinit var binding: FragmentFilterBinding
    val viewModel: SearchProductViewModel by viewModels()
    var filterId = " "
    var filterName = " "
    var filterTermId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_filter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adaptor = AttributeAdaptor {
            filterId = it.slug
            viewModel.getAttributeTerm(it.id.toString().toInt())
        }
        binding.attributeRecyclerView.adapter = adaptor
        viewModel.attributesList.observe(viewLifecycleOwner) {
            adaptor.submitList(it.data)
        }
        val termAdaptor = AttributeTermAdaptor {
            filterName = it.name
            filterTermId = it.id
            Toast.makeText(context, it.slug, Toast.LENGTH_SHORT).show()
        }
        binding.attributeTermRecyclerView.adapter = termAdaptor
        viewModel.attributesTermList.observe(viewLifecycleOwner) {
            termAdaptor.submitList(it.data)
        }
        binding.filterButton.setOnClickListener {
            val action = FilterFragmentDirections.actionFilterFragmentToSearchFragment(filterId,filterTermId,filterName)
            findNavController().navigate(action)
        }

    }


}