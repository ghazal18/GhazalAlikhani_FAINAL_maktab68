package com.example.myapplication.model


data class LineItemBody(
    val id: Int,
    val product_id: Int,
    var quantity: Int,
)