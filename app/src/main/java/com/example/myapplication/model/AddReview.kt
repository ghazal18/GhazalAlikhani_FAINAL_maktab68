package com.example.myapplication.model

data class AddReview(
    val product_id: Int,
    val rating: Int,
    val review: String,
    val reviewer: String,
    val reviewer_email: String
)