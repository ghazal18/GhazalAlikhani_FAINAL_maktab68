package com.example.myapplication.viewModels

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
import com.example.myapplication.network.hasInternetConnection
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
            if (hasInternetConnection(context)) {
                try {
                    val list = productRepository.getCategoriesList()
                    categoriesList.value = list
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
    fun getProductWithCategoryId(categoryId: String) {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val list = productRepository.getCategorysProduct(categoryId)
                    productslist.value = list
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