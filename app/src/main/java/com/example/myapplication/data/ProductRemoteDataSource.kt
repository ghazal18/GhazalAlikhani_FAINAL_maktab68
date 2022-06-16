package com.example.myapplication.data

import com.example.myapplication.model.*
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

    suspend fun search(orderBy: String, searchWord: String, order: String): List<ProductsItem> {
        return apiService.searchProduct(word = searchWord, order = order, orderBy = orderBy)
    }

    suspend fun setOrder(order: OrderBody): Order {
        return apiService.setOrder(order = order)
    }

    suspend fun updateAnOrder(id: Int, order: OrderUpdate): Order {
        return apiService.updateAnOrder(id = id, order = order)
    }

    suspend fun searchForProduct(id: Int): ProductsItem {
        return apiService.searchForProduct(id = id)
    }
}