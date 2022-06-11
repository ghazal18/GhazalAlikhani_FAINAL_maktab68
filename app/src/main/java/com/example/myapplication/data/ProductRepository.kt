package com.example.myapplication.data

import com.example.myapplication.NetworkParams.Companion.ORDER_BY_DATE
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_POPULARITY
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_RATING
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.Customer
import com.example.myapplication.model.ProductsItem
import javax.inject.Inject

class ProductRepository @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    suspend fun getCategoriesList(): List<CategoriesItem> {
        return productRemoteDataSource.getCategorys()
    }

    suspend fun getRatingProduct(): List<ProductsItem> {
        return productRemoteDataSource.getProduct(ORDER_BY_RATING)
    }

    suspend fun getDateProduct(): List<ProductsItem> {
        return productRemoteDataSource.getProduct(ORDER_BY_DATE)
    }

    suspend fun getPopularProduct(): List<ProductsItem> {
        return productRemoteDataSource.getProduct(ORDER_BY_POPULARITY)
    }

    suspend fun getCategorysProduct(categoryId: String): List<ProductsItem> {
        return productRemoteDataSource.getCategorysProduct(categoryId)
    }

    suspend fun setCustomer(customer: Customer): Customer {
        return productRemoteDataSource.setaCustomer(customer)
    }

    suspend fun searchWord(orderBy: String, searchWord: String, order: String): List<ProductsItem> {
        return productRemoteDataSource.search(
            searchWord = searchWord,
            order = order,
            orderBy = orderBy
        )
    }
}