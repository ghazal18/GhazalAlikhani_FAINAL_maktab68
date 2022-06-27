package com.example.myapplication.ui.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var userId = sp.getInt("id", 0)
        println("the user id is $userId")
        if (userId == 0) {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("ابتدا وارد اکانت خود شوید")

                    .setPositiveButton("ساختن اکانت",
                        DialogInterface.OnClickListener { dialog, id ->


                        })
                    .setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                        })

                builder.create()
            }
            if (alertDialog != null) {
                alertDialog.show()
            }
        }

        //get list from details and convert from string to int array
        var productId = sp2.getString("id", "")
        var productCount = sp2.getString("count", "")
        println(productId)
        println(productCount)
        val listOfOrder = mutableListOf<LineItemBody>()
        var list: List<String>? = listOf()
        var countList: List<String>? = listOf()
        if (productId != "" && productCount != "") {
            val listt: List<String>? = productId?.split("-")?.let { listOf(*it.toTypedArray()) }
            list = listt
            val countListt: List<String>? =
                productCount?.split("-")?.let { listOf(*it.toTypedArray()) }
            countList = countListt
        }
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
            responseOrder = it.data
        }

        binding.orderListRecyclerView.adapter = adaptor
        viewModel.orderList.observe(viewLifecycleOwner) {
            if (it != null) {
                adaptor.submitList(it)
            }
            println("the order id is ${responseOrder?.id}")
        }
        viewModel.connectionStatus.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addCouponButton.setOnClickListener {
            var code = binding.couponEditText.text.toString()
            println(code)

            val order = OrderWithCoupon(
                userId,
                orderId,
                "",
                listOfOrder,
                listOf(Coupon(code))
            )
            viewModel.orderWithCoupon(orderId,order)
            println(" this is order user ${order.customer_id} this is order id ${order.id}" +
                    "this is order copone code ${order.coupon_lines[0].code}")

        }

    }


}