package com.example.myapplication.ui.fragments

import android.content.Context
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
import com.example.myapplication.model.*
import com.example.myapplication.viewModels.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

var srcOfProductItem: String = ""

@AndroidEntryPoint
class OrderFragment : Fragment() {
    lateinit var binding: FragmentOrderBinding
    val viewModel: OrderViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var sp2: SharedPreferences
    var responseOrder: Order? = null
    var orderId = 0

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
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var userId = sp.getInt("id", 0)
        //get list from details and convert from string to int array
        var productId = sp2.getString("id", "")
        var productCount = sp2.getString("count", "")
        println(productId)
        println(productCount)
        val listOfOrder = mutableListOf<LineItemBody>()

        val list: List<String>? = productId?.split("-")?.let { listOf(*it.toTypedArray()) }
        val countList: List<String>? = productCount?.split("-")?.let { listOf(*it.toTypedArray()) }
        val idResult = list?.map { it.toInt() }
        val countResult = countList?.map { it.toInt() }
        println(idResult)
        println(countResult)
        //convert id list to list of LineItemBody and add to list
        if (idResult != null && countResult != null) {
            for (i in 0..idResult.size - 1) {

                var lineItemBody = LineItemBody(0, idResult[i], countResult[i])
                listOfOrder.add(lineItemBody)
                println("${lineItemBody.product_id} ,${lineItemBody.quantity} ")

            }
        }
        val order = OrderBody(userId, 0, "", listOfOrder)
        viewModel.order(order)

        viewModel.orderId.observe(viewLifecycleOwner) {
            orderId = it
        }

        val adaptor = OrderAdaptor({
            var updateOrder = OrderUpdate(listOf(LineItemBodyUpdate(it.id, ++it.quantity)))
            responseOrder?.let { it1 -> viewModel.updateAnOrder(it1.id, updateOrder) }

        }, {
            var updateOrder = OrderUpdate(listOf(LineItemBodyUpdate(it.id, --it.quantity)))
            responseOrder?.let { it1 -> viewModel.updateAnOrder(it1.id, updateOrder) }
        })
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            responseOrder = it
        }

        binding.orderListRecyclerView.adapter = adaptor
        viewModel.orderList.observe(viewLifecycleOwner) {
            if (it != null) {
                adaptor.submitList(it)
            }
            println("the order id is ${responseOrder?.id}")
        }

    }


}