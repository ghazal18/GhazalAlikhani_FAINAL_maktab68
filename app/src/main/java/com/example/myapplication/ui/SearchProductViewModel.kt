package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.AttributesItem
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.model.Term
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(val productRepository: ProductRepository) :
    ViewModel() {
    var searchedList = MutableLiveData<List<ProductsItem>>()
    var attributesList = MutableLiveData<List<AttributesItem>>()
    var attributesTermList = MutableLiveData<List<Term>>()
    init {
        getAttribute()
    }

    fun searchItem(searchWord: String, orderBy: String, order: String) {
        viewModelScope.launch {
            val list = productRepository.searchWord(
                searchWord = searchWord,
                orderBy = orderBy,
                order = order
            )
            searchedList.value = list
        }
    }

    fun getAttribute() {
        viewModelScope.launch {
            val listOfAttributes = productRepository.getAttributes()
            attributesList.value = listOfAttributes
        }
    }
    fun getAttributeTerm(id:Int){
        viewModelScope.launch {
            val list = productRepository.getAttributeTerm(id)
            attributesTermList.value = list
        }
    }
}