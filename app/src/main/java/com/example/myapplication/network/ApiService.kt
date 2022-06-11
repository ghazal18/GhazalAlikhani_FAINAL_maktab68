package com.example.myapplication.network

import com.example.myapplication.model.Categories
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.Customer
import com.example.myapplication.model.ProductsItem
import okhttp3.RequestBody
import retrofit2.http.*

const val CONSUMER_KEY = "ck_1638823b5544cd50791d0703153dccdf48c82e8c"
const val CONSUMER_SECRET = "cs_dd772c1e5ac603602694fd19601a830c05c1deb3"
const val ORDER_BY_RATING = "rating"
const val ORDER_BY_DATE = "date"
const val ORDER_BY_POPULARITY = "popularity"

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

    @GET("product")
    suspend fun searchProduct(
        @Query("consumer_key") key: String = CONSUMER_KEY,
        @Query("consumer_secret") secret: String = CONSUMER_SECRET,
        @Query("orderby") orderBy: String,
        @Query("search") word: String = ORDER_BY_DATE
    ): List<ProductsItem>

}
