package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.adaptor.ListOfProductAdaptor
import com.example.myapplication.data.NetworkParams.Companion.ASC_ORDER
import com.example.myapplication.data.NetworkParams.Companion.DESC_ORDER
import com.example.myapplication.data.NetworkParams.Companion.ORDER_BY_DATE
import com.example.myapplication.data.NetworkParams.Companion.ORDER_BY_POPULARITY
import com.example.myapplication.data.NetworkParams.Companion.ORDER_BY_PRICE
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.viewModels.SearchProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    val viewModel: SearchProductViewModel by viewModels()
    val args: SearchFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var search = ""
        var order = ""
        var orderBy = ""
        val adaptor = ListOfProductAdaptor { Product ->
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(Product)
            findNavController().navigate(action)
        }
        var filterSlug = args.filterID
        var filterTermId = args.filterTermID
        var filterName = args.filterName

        viewModel.getProductWithFilter(search, filterSlug, filterTermId)

        viewModel.searchedList.observe(viewLifecycleOwner) {
            binding.filter.text = getString(R.string.filter) + " " + filterName
            if (it != null) {
                adaptor.submitList(it.data)
            }

        }
        binding.movieRecyclerView.adapter = adaptor


        binding.go.setOnClickListener {
            search = binding.searchEditText.text.toString()
            search(orderBy, order, search, filterSlug, filterTermId)
        }
        binding.autoCompleteText.setOnClickListener {
            PopupMenu(context, binding.autoCompleteText).apply {
                menuInflater.inflate(R.menu.menu, menu)
                setOnMenuItemClickListener { item ->
                    binding.autoCompleteText.setText(item.title)
                    when (item.itemId) {
                        R.id.mostSale -> {
                            order = ASC_ORDER
                            orderBy = ORDER_BY_POPULARITY
                        }
                        R.id.chipest -> {
                            order = ASC_ORDER
                            orderBy = ORDER_BY_PRICE
                        }
                        R.id.expensive -> {
                            order = DESC_ORDER
                            orderBy = ORDER_BY_PRICE
                        }
                        R.id.newest -> {
                            order = DESC_ORDER
                            orderBy = ORDER_BY_DATE
                        }
                    }
                    true
                }
                show()
            }
        }
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }


    }

    fun search(
        orderBy: String,
        order: String,
        search: String,
        filterSlug: String,
        filterTermId: Int
    ) {
        viewModel.searchProduct(
            search,
            filterSlug,
            filterTermId,
            orderBy,
            order
        )
    }

}

