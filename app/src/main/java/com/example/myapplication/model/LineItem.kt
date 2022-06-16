package com.example.myapplication.model

data class LineItem(
    val id: Int,
    val name: String,
    val product_id: Int,
    var quantity: Int,
    val total: String
)