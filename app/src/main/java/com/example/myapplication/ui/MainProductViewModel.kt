package com.example.myapplication.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.network.ORDER_BY_DATE
import com.example.myapplication.network.ORDER_BY_RATING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
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
            if (isOnline(context)) {
                val list = productRepository.getRatingProduct()
                productRatingList.value = list
                connectionStatus.value = true
            } else {
                connectionStatus.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getDatingProducts() {
        viewModelScope.launch {
            if (isOnline(context)) {
                val dateList = productRepository.getDateProduct()
                productDataList.value = dateList
                connectionStatus.value = true
            } else {
                connectionStatus.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPopularProducts() {
        viewModelScope.launch {
            if (isOnline(context)) {
                val list = productRepository.getPopularProduct()
                productPopularityList.value = list
                connectionStatus.value = true
            } else {
                connectionStatus.value = false
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }


}