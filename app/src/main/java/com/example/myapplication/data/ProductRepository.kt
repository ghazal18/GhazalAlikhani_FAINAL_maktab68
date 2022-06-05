package com.example.myapplication.data

import com.example.myapplication.model.Categories
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem
import javax.inject.Inject

class ProductRepository @Inject constructor(val productRemoteDataSource:ProductRemoteDataSource){
    suspend fun getCategoriesList():List<CategoriesItem>{
        return productRemoteDataSource.getCategorys()
    }
    suspend fun getProduct():List<ProductsItem>{
        return productRemoteDataSource.getProduct()
    }
}