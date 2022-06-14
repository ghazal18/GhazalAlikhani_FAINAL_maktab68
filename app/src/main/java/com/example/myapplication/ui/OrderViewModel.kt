package com.example.myapplication.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
    var searchProductWithId=  MutableLiveData<ProductsItem?>()



    fun order(order: OrderBody) {
        viewModelScope.launch {
            var list = repository.serOrder(order).line_items
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