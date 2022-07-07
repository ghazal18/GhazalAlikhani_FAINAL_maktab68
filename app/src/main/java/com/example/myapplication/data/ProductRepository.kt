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
        return ApiResponse(productRemoteDataSource.getCategorys())
    }

    suspend fun getRatingProduct(): Resource<List<ProductsItem>> {
        return ApiResponse(productRemoteDataSource.getProduct(ORDER_BY_RATING))
    }

    suspend fun getDateProduct(): Resource<List<ProductsItem>> {
        return ApiResponse(productRemoteDataSource.getProduct(ORDER_BY_DATE))
    }

    suspend fun getPopularProduct(): Resource<List<ProductsItem>> {
        return ApiResponse(productRemoteDataSource.getProduct(ORDER_BY_POPULARITY))
    }

    suspend fun getCategorysProduct(categoryId: String): Resource<List<ProductsItem>> {
        return ApiResponse(productRemoteDataSource.getCategorysProduct(categoryId))
    }

    suspend fun setCustomer(customer: Customer): Resource<Customer> {
        return ApiResponse(productRemoteDataSource.setaCustomer(customer))
    }

    suspend fun searchWord(
        orderBy: String,
        searchWord: String,
        order: String
    ): Resource<List<ProductsItem>> {
        return ApiResponse(
            productRemoteDataSource.search(
                searchWord = searchWord,
                order = order,
                orderBy = orderBy
            )
        )
    }

    suspend fun serOrder(order: OrderBody): Resource<Order> {
        return ApiResponse(productRemoteDataSource.setOrder(order))
    }

    suspend fun setWithCouponOrder(order: OrderWithCoupon): Resource<Order> {
        return ApiResponse(productRemoteDataSource.setWithCouponOrder(order = order))
    }

    suspend fun updateAnOrder(id: Int, order: OrderUpdate): Resource<Order> {
        return ApiResponse(productRemoteDataSource.updateAnOrder(id, order))
    }

    suspend fun getReviews(productId: Int): Resource<List<ReviewsItem>> {
        return ApiResponse(productRemoteDataSource.getReviews(productId))
    }

    suspend fun setReview(newReview: AddReview): Resource<ReviewsItem> {
        return ApiResponse(productRemoteDataSource.setReview(newReview))
    }

    suspend fun deleteReview(id: Int): Resource<ReviewsItem> {
        return ApiResponse(productRemoteDataSource.deleteReview(id))
    }

    suspend fun updateReview(id: Int, review: UpdateReview): Resource<ReviewsItem> {
        return ApiResponse(productRemoteDataSource.updateReview(id, review))
    }

    suspend fun getAttributes(): Resource<List<AttributesItem>> {
        return ApiResponse(productRemoteDataSource.getAttributes())
    }

    suspend fun getAttributeTerm(id: Int): Resource<List<Term>> {
        return ApiResponse(productRemoteDataSource.getAttributeTerm(id))
    }

    suspend fun getProductWithFilter(
        search: String,
        attribute: String,
        attributeTerm: Int
    ): Resource<List<ProductsItem>> {
        return ApiResponse(
            productRemoteDataSource.getProductWithFilter(
                search = search,
                attribute = attribute,
                attributeTerm = attributeTerm
            )
        )
    }

    suspend fun searchProduct(
        search: String,
        attribute: String,
        attributeTerm: Int,
        orderBy: String, order: String
    ): Resource<List<ProductsItem>> {
        return ApiResponse(
            productRemoteDataSource.searchProduct(
                attribute = attribute,
                attributeTerm = attributeTerm,
                search = search,
                order = order, orderBy = orderBy
            )
        )
    }

    fun <T> ApiResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful && response.body() != null) {
            Resource.Success(response.body())
        } else {
            Resource.Error(
                massage = response.message().toString(),
                code = response.code().toString()
            )
        }
    }

}