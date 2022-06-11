package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.ProductsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(val productRepository: ProductRepository) :
    ViewModel() {
    var searchedList = MutableLiveData<List<ProductsItem>>()

    fun searchItem(searchWord: String,orderBy:String) {
        viewModelScope.launch {
            val list = productRepository.searchWord(orderBy,searchWord)
            searchedList.value = list
        }
    }
}