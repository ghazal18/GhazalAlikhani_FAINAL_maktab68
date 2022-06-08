package com.example.myapplication.ui

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
import com.example.myapplication.R
import com.example.myapplication.adaptor.ListOfCategoriesAdaptor
import com.example.myapplication.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    val viewModel: CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adaptor = ListOfCategoriesAdaptor() {
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailsFragment(it.id.toString())
            findNavController().navigate(action)
        }
        binding.categoryRecyclerView.adapter = adaptor
        viewModel.categoriesList.observe(viewLifecycleOwner) {
            if (it != null) {
                adaptor.submitList(it)
            }
        }
        viewModel.connectionStatus.observe(viewLifecycleOwner){
            if (!it){
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
}