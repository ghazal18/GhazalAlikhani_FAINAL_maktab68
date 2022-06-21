package com.example.myapplication.ui.fragments

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.adaptor.ListOfProductAdaptor
import com.example.myapplication.databinding.FragmentShowAllBinding
import com.example.myapplication.viewModels.MainProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAllFragment : Fragment() {
    val args: ShowAllFragmentArgs by navArgs()
    val viewModel: MainProductViewModel by viewModels()
    lateinit var binding: FragmentShowAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_all, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var type = args.listType
        val adaptor = ListOfProductAdaptor() { Product ->
            val action = ShowAllFragmentDirections.actionShowAllFragmentToDetailsFragment(Product)
            findNavController().navigate(action)

        }
        binding.showAllRecyclerView.adapter = adaptor
        if (type == "best") {
            viewModel.productRatingList.observe(viewLifecycleOwner) {
                if (it != null) {
                    adaptor.submitList(it)
                }
            }
        } else if (type == "new") {
            viewModel.productDataList.observe(viewLifecycleOwner) {
                if (it != null) {
                    adaptor.submitList(it)
                }
            }

        } else {
            viewModel.productPopularityList.observe(viewLifecycleOwner) {
                if (it != null) {
                    adaptor.submitList(it)
                }
            }

        }
        viewModel.connectionStatus.observe(viewLifecycleOwner){
            if (!it){
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
}