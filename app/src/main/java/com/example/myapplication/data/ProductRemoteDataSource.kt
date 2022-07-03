package com.example.myapplication.data

import com.example.myapplication.model.*
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.Resource
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {
    suspend fun getCategorys(): Response<List<CategoriesItem>> {
        return apiService.getCategories()
    }

    suspend fun getProduct(oderBy: String): Response<List<ProductsItem>> {
        return apiService.getProducts(orderBy = oderBy)
    }

    suspend fun getCategorysProduct(categoryId: String): Response<List<ProductsItem>> {
        return apiService.getProductsWithCategory(categoryy = categoryId)
    }

    suspend fun setaCustomer(customer: Customer): Response<Customer> {
        return apiService.createAccount(customer = customer)
    }

    suspend fun search(
        orderBy: String,
        searchWord: String,
        order: String
    ): Response<List<ProductsItem>> {
        return apiService.searchProduct(word = searchWord, order = order, orderBy = orderBy)
    }

    suspend fun setOrder(order: OrderBody): Response<Order> {
        return apiService.setOrder(order = order)
    }

    suspend fun setWithCouponOrder(id: Int, order: OrderWithCoupon): Response<Order> {
        return apiService.setWithCouponOrder(id = id, order = order)
    }

    suspend fun updateAnOrder(id: Int, order: OrderUpdate): Response<Order> {
        return apiService.updateAnOrder(id = id, order = order)
    }

    suspend fun getReviews(productId: Int): Response<List<ReviewsItem>> {
        return apiService.getReviews(id = productId)
    }

    suspend fun setReview(newReview: AddReview): Response<ReviewsItem> {
        return apiService.addReview(reviewBody = newReview)
    }

    suspend fun deleteReview(id: Int): Response<ReviewsItem> {
        return apiService.deleteAReview(id = id)
    }

    suspend fun getAttributes(): Response<List<AttributesItem>> {
        return apiService.getAttributes()
    }

    suspend fun getAttributeTerm(id: Int): Response<List<Term>> {
        return apiService.getAttributeTerm(attribute_id = id)
    }

    suspend fun getProductWithFilter(
        search: String,
        attribute: String,
        attributeTerm: Int
    ): Response<List<ProductsItem>> {
        return apiService.getProductWithFilter(
            attribute = attribute,
            attribute_term = attributeTerm,
            search = search
        )
    }

    suspend fun searchProduct(
        search: String,
        attribute: String,
        attributeTerm: Int,
        orderBy: String, order: String
    ): Response<List<ProductsItem>> {
        return apiService.searchProduct2(
            attribute = attribute,
            attribute_term = attributeTerm,
            search = search,
            order = order, orderBy = orderBy
        )
    }
}