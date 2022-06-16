package com.example.myapplication.model

data class OrderUpdate(
    val line_items: List<LineItemBodyUpdate>,
)