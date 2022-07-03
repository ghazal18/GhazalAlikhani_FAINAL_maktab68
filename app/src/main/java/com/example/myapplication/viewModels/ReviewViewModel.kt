package com.example.myapplication.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.model.AddReview
import com.example.myapplication.model.ReviewsItem
import com.example.myapplication.model.UpdateReview
import com.example.myapplication.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    val repository: ProductRepository,
    application: Application
) : AndroidViewModel(application) {
    var reviewLiveData = MutableLiveData<Resource<ReviewsItem>>()
    fun setReview(review: AddReview) {
        viewModelScope.launch {
            val data = repository.setReview(review)
            reviewLiveData.value = data
        }
    }

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            val data = repository.deleteReview(id)
        }
    }

    fun updateReview(id: Int, review: UpdateReview) {
        viewModelScope.launch {
            val date = repository.updateReview(id, review)
        }
    }
}