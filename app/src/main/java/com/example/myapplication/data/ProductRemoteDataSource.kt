package com.example.myapplication.data

import com.example.myapplication.model.Categories
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.network.ApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {
    suspend fun getCategorys(): List<CategoriesItem> {
        return apiService.getCategories()
    }

    suspend fun getProduct(oderBy: String): List<ProductsItem> {
        return apiService.getProducts(orderBy = oderBy)
    }

    suspend fun getCategorysProduct(categoryId: String): List<ProductsItem> {
        return apiService.getProductsWithCategory(categoryy = categoryId)
    }
}