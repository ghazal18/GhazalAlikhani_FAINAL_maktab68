package com.example.myapplication.ui

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
import javax.inject.Inject

@HiltViewModel
class MainProductViewModel @Inject constructor(val productRepository: ProductRepository) :
    ViewModel() {
    var categorieslist = MutableLiveData<List<CategoriesItem>>()
    var productPopularityList = MutableLiveData<List<ProductsItem>>()
    var productDataList = MutableLiveData<List<ProductsItem>>()
    var productRatingList = MutableLiveData<List<ProductsItem>>()

    init {
        getCategories()
        getRatingProducts()
        getDatingProducts()
        getPopularProducts()
    }

    fun getCategories() {
        viewModelScope.launch {
            val list = productRepository.getCategoriesList()
            categorieslist.value = list
        }
    }

    fun getRatingProducts() {
        viewModelScope.launch {
            val list = productRepository.getRatingProduct()
            productRatingList.value = list
        }
    }
    fun getDatingProducts() {
        viewModelScope.launch {
            val dateList = productRepository.getDateProduct()
            productDataList.value = dateList
        }
    }
    fun getPopularProducts() {
        viewModelScope.launch {
            val dateList = productRepository.getPopularProduct()
            productPopularityList.value = dateList
        }
    }


}