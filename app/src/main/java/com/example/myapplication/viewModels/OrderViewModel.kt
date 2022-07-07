package com.example.myapplication.viewModels


import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.*
import com.example.myapplication.network.Resource
import com.example.myapplication.network.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val repository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    val orderList = MutableLiveData<List<LineItem>>()
    val orderLiveData = MutableLiveData<Resource<Order>>()
    val productsItem = MutableLiveData<ProductsItem>()
    val productsItemList = mutableListOf<ProductsItem>()
    var orderId = MutableLiveData<Int>()
    var connectionStatus = MutableLiveData(true)
    val listt = MutableLiveData<List<ProductsItem>>(productsItemList)
    val listttt = MutableLiveData<MutableList<ProductsItem>>()

    @RequiresApi(Build.VERSION_CODES.M)
    fun order(order: OrderBody) {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    var order = repository.serOrder(order)
                    orderId.value = order.data?.id
                    orderLiveData.value = order
                    var list = order.data?.line_items
                    orderList.value = list!!
                    connectionStatus.value = true
                } catch (e: Exception) {
                    connectionStatus.value = false
                }
            } else {
                connectionStatus.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun updateAnOrder(id: Int, order: OrderUpdate) {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    var list = repository.updateAnOrder(id, order).data?.line_items
                    orderList.value = list!!
                    connectionStatus.value = true
                } catch (e: Exception) {
                    connectionStatus.value = false
                }
            } else {
                connectionStatus.value = false
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun orderWithCoupon(order: OrderWithCoupon) {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    var list = repository.setWithCouponOrder(order).data?.line_items
                    orderList.value = list!!
                } catch (e: Exception) {
                    connectionStatus.value = false
                }
            } else {
                connectionStatus.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getProductWithId(id: Int) {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    var list = repository.getProductWithId(id).data

                    if (list != null) {
                        listttt.value?.add(list)
                    }

                    productsItem.value = list!!
                } catch (e: Exception) {
                    connectionStatus.value = false
                }
            } else {
                connectionStatus.value = false
            }
        }
    }
}





