package com.example.myapplication.data

import com.example.myapplication.model.Categories
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.network.ORDER_BY_DATE
import com.example.myapplication.network.ORDER_BY_POPULARITY
import com.example.myapplication.network.ORDER_BY_RATING
import javax.inject.Inject

class ProductRepository @Inject constructor(val productRemoteDataSource:ProductRemoteDataSource){
    suspend fun getCategoriesList():List<CategoriesItem>{
        return productRemoteDataSource.getCategorys()
    }
    suspend fun getRatingProduct():List<ProductsItem>{
        return productRemoteDataSource.getProduct(ORDER_BY_RATING)
    }
    suspend fun getDateProduct():List<ProductsItem>{
        return productRemoteDataSource.getProduct(ORDER_BY_DATE)
    }
    suspend fun getPopularProduct():List<ProductsItem>{
        return productRemoteDataSource.getProduct(ORDER_BY_POPULARITY)
    }
}