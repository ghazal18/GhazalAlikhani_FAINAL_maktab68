package com.example.myapplication.network

import com.example.myapplication.model.Categories
import com.example.myapplication.model.CategoriesItem
import com.example.myapplication.model.ProductsItem
import retrofit2.http.GET
import retrofit2.http.Query

const val CONSUMER_KEY = "ck_6b55bb0ff3ea0b7bf4c0aa879af50061964ce38f"
const val CONSUMER_SECRET = "cs_9b09e5125acffdd27bbe72843ced49db5f8bffb4"
const val ORDER_BY_RATING = "rating"
const val ORDER_BY_DATE = "date"
const val ORDER_BY_POPULARITY = "popularity"

interface ApiService {
    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key") key:String = CONSUMER_KEY,
        @Query("consumer_secret") secret:String = CONSUMER_SECRET
    ):List<CategoriesItem>

    @GET("products")
    suspend fun getProducts(
        @Query("consumer_key") key:String = CONSUMER_KEY,
        @Query("consumer_secret") secret:String = CONSUMER_SECRET,
        @Query("orderby")orderBy :String
    ):List<ProductsItem>

    @GET("products")
    suspend fun getProductsWithCategory(
        @Query("consumer_key") key:String = CONSUMER_KEY,
        @Query("consumer_secret") secret:String = CONSUMER_SECRET,
        @Query("category")categoryy :String
    ):List<ProductsItem>


}
