package com.example.myapplication.model

data class Order(
    val customer_id: Int,
    val id: Int,
    val total: String,
    val discount_total: String,
    val line_items: List<LineItem>,
    val coupon_lines:List<Coupon>
)