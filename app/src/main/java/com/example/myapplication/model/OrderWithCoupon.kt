package com.example.myapplication.model


data class OrderWithCoupon(
    val customer_id: Int,
    val id: Int,
    val total: String,
    val line_items: List<LineItemBody>,
    val coupon_lines:List<Coupon>
)