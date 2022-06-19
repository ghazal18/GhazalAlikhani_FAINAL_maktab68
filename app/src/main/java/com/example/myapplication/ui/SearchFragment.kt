package com.example.myapplication.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.NetworkParams.Companion.ASC_ORDER
import com.example.myapplication.NetworkParams.Companion.DESC_ORDER
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_DATE
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_POPULARITY
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_PRICE
import com.example.myapplication.R
import com.example.myapplication.adaptor.ListOfProductAdaptor
import com.example.myapplication.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    val viewModel: SearchProductViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var search = ""
        val adaptor = ListOfProductAdaptor {

        }

        viewModel.searchedList.observe(viewLifecycleOwner) {
            if (it != null) {
                adaptor.submitList(it)
            }

        }
        binding.movieRecyclerView.adapter = adaptor
        /*binding.searchEditText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })*/

        binding.go.setOnClickListener {
            search = binding.searchEditText.text.toString()
        }
        binding.autoCompleteText.setOnClickListener {
            PopupMenu(context, binding.autoCompleteText).apply {
                menuInflater.inflate(R.menu.menu, menu)
                setOnMenuItemClickListener { item ->
                    binding.autoCompleteText.setText(item.title)
                    when (item.itemId) {
                        R.id.mostSale -> viewModel.searchItem(
                            search,
                            ORDER_BY_POPULARITY,
                            ASC_ORDER
                        )
                        R.id.chipest -> viewModel.searchItem(
                            search,
                            ORDER_BY_PRICE,
                            ASC_ORDER
                        )
                        R.id.expensive -> viewModel.searchItem(
                            search,
                            ORDER_BY_PRICE,
                            DESC_ORDER
                        )
                        R.id.newest -> viewModel.searchItem(
                            search,
                            ORDER_BY_DATE,
                            DESC_ORDER
                        )
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


}

