package com.example.myapplication.data


import com.example.myapplication.data.NetworkParams.Companion.ORDER_BY_DATE
import com.example.myapplication.data.NetworkParams.Companion.ORDER_BY_POPULARITY
import com.example.myapplication.data.NetworkParams.Companion.ORDER_BY_RATING
import com.example.myapplication.model.*
import com.example.myapplication.network.Resource
import com.example.myapplication.network.errorCode
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(val productRemoteDataSource: ProductRemoteDataSource) {
    suspend fun getCategoriesList(): Resource<List<CategoriesItem>> {
        return  ApiResponse(productRemoteDataSource.getCategorys())
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

    suspend fun setCustomer(customer: Customer): Resource<Customer> {
        return ApiResponse(productRemoteDataSource.setaCustomer(customer))
    }

    suspend fun searchWord(orderBy: String, searchWord: String, order: String): List<ProductsItem> {
        return productRemoteDataSource.search(
            searchWord = searchWord,
            order = order,
            orderBy = orderBy
        )
    }

    suspend fun serOrder(order: OrderBody): Order {
        return productRemoteDataSource.setOrder(order)
    }

    suspend fun updateAnOrder(id: Int, order: OrderUpdate): Order {
        return productRemoteDataSource.updateAnOrder(id, order)
    }

    suspend fun searchForProduct(id: Int): ProductsItem {
        return productRemoteDataSource.searchForProduct(id)
    }

    suspend fun getReviews(productId: Int): List<ReviewsItem> {
        return productRemoteDataSource.getReviews(productId)
    }

    suspend fun getAttributes(): List<AttributesItem> {
        return productRemoteDataSource.getAttributes()
    }

    suspend fun getAttributeTerm(id: Int): List<Term> {
        return productRemoteDataSource.getAttributeTerm(id)
    }

    suspend fun getProductWithFilter(
        search: String,
        attribute: String,
        attributeTerm: Int
    ): List<ProductsItem> {
        return productRemoteDataSource.getProductWithFilter(
            search = search,
            attribute = attribute,
            attributeTerm = attributeTerm
        )
    }
    suspend fun searchProduct(
        search: String,
        attribute: String,
        attributeTerm: Int,
        orderBy: String, order: String
    ): List<ProductsItem> {
        return productRemoteDataSource.searchProduct(
            attribute = attribute,
            attributeTerm = attributeTerm,
            search = search,
            order = order, orderBy = orderBy
        )
    }
    fun <T> ApiResponse(response :Response<T>):Resource<T>{
        return if(response.isSuccessful && response.body() != null){
            Resource.Success(response.body())
        }else{
            Resource.Error(massage = response.message().toString(),code = response.code().toString())
        }
    }

}