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
import com.example.myapplication.databinding.FragmentCategoryDetailsBinding
import com.example.myapplication.viewModels.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailsFragment : Fragment() {
    val args: CategoryDetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentCategoryDetailsBinding
    val viewModel : CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_category_details,
            container,
            false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adaptor = ListOfProductAdaptor() { Product ->
            val action = CategoryDetailsFragmentDirections.actionCategoryDetailsFragmentToDetailsFragment(Product)
            findNavController().navigate(action)

        }
        binding.productByCategoryRecyclerView.adapter = adaptor
        var id = args.id
        viewModel.getProductWithCategoryId(id)
        viewModel.productslist.observe(viewLifecycleOwner){
            adaptor.submitList(it.data)
        }
        viewModel.connectionStatus.observe(viewLifecycleOwner){
            if (!it){
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }

    }
}