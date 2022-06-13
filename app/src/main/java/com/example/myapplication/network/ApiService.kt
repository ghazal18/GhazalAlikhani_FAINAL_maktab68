package com.example.myapplication.network

import com.example.myapplication.NetworkParams.Companion.CONSUMER_KEY
import com.example.myapplication.NetworkParams.Companion.CONSUMER_SECRET
import com.example.myapplication.NetworkParams.Companion.DESC_ORDER
import com.example.myapplication.NetworkParams.Companion.ORDER_BY_DATE
import com.example.myapplication.model.*
import retrofit2.http.*


interface ApiService {
    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET
    ): List<CategoriesItem>

    @GET("products")
    suspend fun getProducts(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Query("orderby") orderBy: String
    ): List<ProductsItem>

    @GET("products")
    suspend fun getProductsWithCategory(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Query("category") categoryy: String
    ): List<ProductsItem>

    @POST("customers")
    suspend fun createAccount(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Body customer: Customer
    ): Customer

    @GET("products")
    suspend fun searchProduct(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Query("search") word: String = ORDER_BY_DATE,
        @Query("order") order: String = DESC_ORDER,
        @Query("orderby") orderBy: String
    ): List<ProductsItem>

    @POST("orders")
    suspend fun setOrder(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Body order: OrderBody
    ): Order


}
