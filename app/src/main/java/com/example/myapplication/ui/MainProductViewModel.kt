package com.example.myapplication.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.NetworkParams.Companion.ASC_ORDER
import com.example.myapplication.NetworkParams.Companion.DESC_ORDER
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_DATE
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.AttributesItem
import com.example.myapplication.model.Image
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.model.ReviewsItem
import com.example.myapplication.network.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class MainProductViewModel @Inject constructor(
    val productRepository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {

    var number = MutableLiveData(1)
    private val context = getApplication<Application>().applicationContext
    var productPopularityList = MutableLiveData<List<ProductsItem>>()
    var productDataList = MutableLiveData<List<ProductsItem>>()
    var productRatingList = MutableLiveData<List<ProductsItem>>()
    var reviewsList = MutableLiveData<List<ReviewsItem>>()

    var connectionStatus = MutableLiveData(true)


    init {
        getRatingProducts()
        getDatingProducts()
        getPopularProducts()
        slider()
    }

    fun decTheNumber() {
        if (number.value == 0) {
            number.value = 0
        } else {
            number.value = number.value?.minus(1)
        }
    }

    fun incTheNumber() {
        number.value = number.value?.plus(1)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getRatingProducts() {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val list = productRepository.getRatingProduct()
                    productRatingList.value = list
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
    fun getDatingProducts() {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val dateList = productRepository.getDateProduct()
                    productDataList.value = dateList
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
    fun getPopularProducts() {
        viewModelScope.launch {
            if (hasInternetConnection(context)) {
                try {
                    val list = productRepository.getPopularProduct()
                    productPopularityList.value = list
                    connectionStatus.value = true
                } catch (e: Exception) {
                    connectionStatus.value = false
                }
            } else {
                connectionStatus.value = false
            }

        }
    }

    var sliderPhoto = MutableLiveData<List<Image>>()

    fun slider() {
        viewModelScope.launch {
            val list = productRepository.searchWord(
                searchWord = "تخفیفات",
                orderBy = ORDER_BY_DATE,
                order = ASC_ORDER
            )

            sliderPhoto.value = list[0].images
        }
    }

    fun getReviews(id: Int) {
        viewModelScope.launch {
            val listOfReview = productRepository.getReviews(id)
            reviewsList.value = listOfReview
        }
    }


}