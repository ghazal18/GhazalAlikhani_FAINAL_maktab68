package com.example.myapplication.network

import com.example.myapplication.data.NetworkParams.Companion.CONSUMER_KEY
import com.example.myapplication.data.NetworkParams.Companion.CONSUMER_SECRET
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
        @Query("search") word: String,
        @Query("order") order: String,
        @Query("orderby") orderBy: String
    ): List<ProductsItem>

    @POST("orders")
    suspend fun setOrder(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Body order: OrderBody
    ): Order

    @PUT("orders/{id}")
    suspend fun updateAnOrder(
        @Path(value = "id") id: Int,
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Body order: OrderUpdate
    ): Order

    @GET("products/{id}")
    suspend fun searchForProduct(
        @Path(value = "id") id: Int,
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET
    ): ProductsItem

    @GET("products/reviews")
    suspend fun getReviews(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Query("product") id: Int
    ): List<ReviewsItem>

    @GET("products/attributes")
    suspend fun getAttributes(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
    ): List<AttributesItem>

    @GET("products/attributes/{attribute_id}/terms")
    suspend fun getAttributeTerm(
        @Path(value = "attribute_id") attribute_id: Int,
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
    ): List<Term>

    @GET("products")
    suspend fun getProductWithFilter(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Query("attribute")attribute:String,
        @Query("attribute_term")attribute_term:Int,
        @Query("search")search:String
    ):List<ProductsItem>


}
