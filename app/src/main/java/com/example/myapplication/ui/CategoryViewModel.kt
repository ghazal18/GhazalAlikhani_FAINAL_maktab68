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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class CategoryViewModel @Inject constructor(
    val productRepository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {


    private val context = getApplication<Application>().applicationContext


    var categoriesList = MutableLiveData<List<CategoriesItem>>()
    var productslist = MutableLiveData<List<ProductsItem>>()
    var connectionStatus = MutableLiveData<Boolean>(true)


    init {
        getCategories()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getCategories() {
        viewModelScope.launch {
            if (isOnline(context)) {
                val list = productRepository.getCategoriesList()
                categoriesList.value = list
                connectionStatus.value = true
            } else {
                connectionStatus.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getProductWithCategoryId(categoryId: String) {
        viewModelScope.launch {
            if (isOnline(context)) {
                val list = productRepository.getCategorysProduct(categoryId)
                productslist.value = list
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