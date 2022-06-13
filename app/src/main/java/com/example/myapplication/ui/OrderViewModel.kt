package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.LineItem
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val repository: ProductRepository) : ViewModel() {
    val orderList = MutableLiveData<List<LineItem>>()
    fun order(order: OrderBody) {
        viewModelScope.launch {
            var list = repository.serOrder(order).line_items
            orderList.value =list
        }
    }

}