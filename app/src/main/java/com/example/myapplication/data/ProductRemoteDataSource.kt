package com.example.myapplication.data

import com.example.myapplication.model.Categories
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.Customer
import com.example.myapplication.model.ProductsItem
import com.example.myapplication.network.ApiService
import okhttp3.RequestBody
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

    suspend fun setaCustomer(customer: Customer): Customer {
        return apiService.createAccount(customer = customer)
    }

    suspend fun search(orderBy: String, searchWord: String) : List<ProductsItem>{
        return apiService.searchProduct(orderBy = orderBy, word = searchWord)
    }
}