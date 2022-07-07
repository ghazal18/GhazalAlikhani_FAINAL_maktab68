package com.example.myapplication.data

import com.example.myapplication.model.ProductsItem

object ArrayOfProductDetails {
    var idOfProductArray: MutableList<Int> = mutableListOf<Int>()
    var numberOfProductArray: MutableList<Int> = mutableListOf<Int>()
    var orderProductList = mutableListOf<ProductsItem>()
    lateinit var orderCountProductList :List<Int>
}
