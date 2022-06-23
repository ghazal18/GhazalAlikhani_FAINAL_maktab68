package com.example.myapplication.viewModels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.Customer
import com.example.myapplication.network.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val productRepository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var customerLiveData = MutableLiveData<Customer?>()
    var connectionStatus = MutableLiveData(true)

    @RequiresApi(Build.VERSION_CODES.M)
    fun getAndSetCustomer(customer: Customer) {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val custom = productRepository.setCustomer(customer)
                    customerLiveData.value = custom
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