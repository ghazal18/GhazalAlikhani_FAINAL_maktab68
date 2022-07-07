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
import com.example.myapplication.adaptor.OrderProductAdaptor
import com.example.myapplication.adaptor.ProductAdaptor
import com.example.myapplication.data.ArrayOfProductDetails
import com.example.myapplication.databinding.FragmentOrderBinding
import com.example.myapplication.model.*
import com.example.myapplication.viewModels.MainProductViewModel
import com.example.myapplication.viewModels.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {
    lateinit var binding: FragmentOrderBinding
    val viewModel: OrderViewModel by viewModels()
    lateinit var sp: SharedPreferences
    lateinit var sp2: SharedPreferences
    var responseOrder: Order? = null
    var orderId = 0
    var code = ""

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
            showDialog()
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
        val countResult = countList?.map { it.toInt() }?.toMutableList()
        if (countResult != null) {
            ArrayOfProductDetails.orderCountProductList = countResult
        }



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

        if (idResult != null) {
            for (i in 0..idResult.size - 1) {
                viewModel.getProductWithId(idResult[i])
            }
        }

        val adaptott = countResult?.let {
            OrderProductAdaptor(it, {
                listOfOrder[it].quantity = ++listOfOrder[it].quantity
            }, {
                listOfOrder[it].quantity = --listOfOrder[it].quantity
            })
        }
        binding.orderListRecyclerView.adapter = adaptott
        adaptott?.submitList(ArrayOfProductDetails.orderProductList)

        binding.buttonSetOrder.setOnClickListener {
            if (code != "") {
                val order = OrderWithCoupon(
                    userId,
                    0,
                    "",
                    listOfOrder,
                    listOf(Coupon(code))
                )
                viewModel.orderWithCoupon(order)
            } else {
                val order = OrderBody(userId, 0, "", listOfOrder)
                viewModel.order(order)
            }
            ArrayOfProductDetails.orderProductList = mutableListOf<ProductsItem>()
            ArrayOfProductDetails.orderCountProductList = listOf()
        }


        viewModel.orderId.observe(viewLifecycleOwner) {
            orderId = it
            println("the order id is $it")
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
            if (it.code != "201") {
                Toast.makeText(context, "مشکلی در ثبت سفارش شما پیش امده", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.addCouponButton.setOnClickListener {
            code = binding.couponEditText.text.toString()
            println(code)
        }

    }

    fun showDialog() {
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


}