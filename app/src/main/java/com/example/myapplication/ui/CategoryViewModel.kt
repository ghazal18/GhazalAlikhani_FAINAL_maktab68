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
class CategoryViewModel @Inject constructor(val productRepository: ProductRepository): ViewModel (){
    var categorieslist = MutableLiveData<List<CategoriesItem>>()


    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            val list = productRepository.getCategoriesList()
            categorieslist.value = list
        }
    }
}