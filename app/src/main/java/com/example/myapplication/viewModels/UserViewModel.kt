package com.example.myapplication.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val productRepository: ProductRepository) : ViewModel() {
    var customerLiveData = MutableLiveData<Customer?>()


    fun getAndSetCustomer(customer: Customer) {
        viewModelScope.launch {
            val custom = productRepository.setCustomer(customer)
            customerLiveData.value = custom
        }
    }
}