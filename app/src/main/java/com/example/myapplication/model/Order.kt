package com.example.myapplication.model

data class Order(
    val customer_id: Int,
    val id: Int,
    val total: String,
    val line_items: List<LineItem>,
)