package com.example.myapplication.viewModels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val repository: ProductRepository) : ViewModel() {
    val orderList = MutableLiveData<List<LineItem>>()
    val orderLiveData = MutableLiveData<Order>()
    var orderId = MutableLiveData<Int>()
    var searchProductWithId=  MutableLiveData<ProductsItem?>()



    fun order(order: OrderBody) {
        viewModelScope.launch {
            var order = repository.serOrder(order)
            orderId.value = order.id
            orderLiveData.value = order
            var list = order.line_items
            orderList.value =list
        }
    }
    fun updateAnOrder(id: Int,order: OrderUpdate){
        viewModelScope.launch {
            var list = repository.updateAnOrder(id,order).line_items
            orderList.value =list
        }
    }
    fun searchForProductWithId(id: Int) {
        viewModelScope.launch {
            val productWithId = repository.searchForProduct(id)
            searchProductWithId.value = productWithId
        }
    }



}