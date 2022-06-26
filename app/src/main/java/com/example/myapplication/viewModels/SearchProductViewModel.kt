package com.example.myapplication.viewModels

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

    fun getAttribute() {
        viewModelScope.launch {
            val listOfAttributes = productRepository.getAttributes()
            attributesList.value = listOfAttributes
        }
    }

    fun getAttributeTerm(id: Int) {
        viewModelScope.launch {
            val list = productRepository.getAttributeTerm(id)
            attributesTermList.value = list
        }
    }

    fun getProductWithFilter(search: String, attribute: String, attributeTerm: Int) {
        viewModelScope.launch {
            val list = productRepository.getProductWithFilter(search, attribute, attributeTerm)
            searchedList.value = list
        }
    }

    fun searchProduct(
        search: String,
        attribute: String,
        attributeTerm: Int,
        orderBy: String, order: String
    ) {
        viewModelScope.launch {
            val list = productRepository.searchProduct(
                attribute = attribute,
                attributeTerm = attributeTerm,
                search = search,
                order = order, orderBy = orderBy
            )
            searchedList.value = list
        }
    }
}