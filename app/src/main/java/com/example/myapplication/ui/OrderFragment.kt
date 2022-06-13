package com.example.myapplication.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.adaptor.OrderAdaptor
import com.example.myapplication.databinding.FragmentOrderBinding
import com.example.myapplication.model.LineItem
import com.example.myapplication.model.LineItemBody
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderBody
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderFragment : Fragment() {
    lateinit var binding: FragmentOrderBinding
    val viewModel: OrderViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var sp2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = this.requireActivity().getSharedPreferences("accountId", Context.MODE_PRIVATE)
        sp2 = this.requireActivity().getSharedPreferences("order", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var userId = sp.getInt("id", 0)
        var productId = sp2.getInt("id", 0)
        val listOfOrder = mutableListOf<LineItemBody>()

        val order = OrderBody(userId,0,"",listOfOrder)
        viewModel.order(order)
        val adaptor = OrderAdaptor {

        }
        binding.orderListRecyclerView.adapter = adaptor
        viewModel.orderList.observe(viewLifecycleOwner) {
            adaptor.submitList(it)
        }

    }
}