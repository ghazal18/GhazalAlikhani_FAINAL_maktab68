package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainProductViewModel @Inject constructor(val productRepository: ProductRepository):ViewModel() {
    var categorieslist = MutableLiveData<List<CategoriesItem>>()
    var productAverageList = MutableLiveData<List<ProductsItem>>()
    var productDataList = MutableLiveData<List<ProductsItem>>()
    init {
        getCategories()
        getProducts()
    }
    fun getCategories(){
        viewModelScope.launch {
            val list =productRepository.getCategoriesList()
            categorieslist.value = list
        }
    }
    fun getProducts(){
        viewModelScope.launch {
            val list = productRepository.getProduct()
            val sortedByAverageList = list.sortedByDescending { it.average_rating }
            val sortedByDateList = list.sortedByDescending { it.date_created }
            productAverageList.value = sortedByAverageList
            productDataList.value = sortedByDateList
        }
    }
}