package com.example.myapplication.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.network.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class MainProductViewModel @Inject constructor(
    val productRepository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {


    private val context = getApplication<Application>().applicationContext
    var productPopularityList = MutableLiveData<List<ProductsItem>>()
    var productDataList = MutableLiveData<List<ProductsItem>>()
    var productRatingList = MutableLiveData<List<ProductsItem>>()
    var connectionStatus = MutableLiveData<Boolean>(true)

    init {
        getRatingProducts()
        getDatingProducts()
        getPopularProducts()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getRatingProducts() {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val list = productRepository.getRatingProduct()
                    productRatingList.value = list
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
    fun getDatingProducts() {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val dateList = productRepository.getDateProduct()
                    productDataList.value = dateList
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
    fun getPopularProducts() {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val list = productRepository.getPopularProduct()
                    productPopularityList.value = list
                    connectionStatus.value = true
                } catch (e: Exception) {
                    connectionStatus.value = false
                }
            } else {
                connectionStatus.value = false
            }

        }
    }






}