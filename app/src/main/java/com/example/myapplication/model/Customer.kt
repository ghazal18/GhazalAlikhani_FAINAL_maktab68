package com.example.myapplication.model

data class Customer(
    val id: Int = 0,
    val first_name: String ,
    val last_name: String,
    val email: String ,
    val username: String ,
    val billing: Billing ,
    val shipping: Shipping

)