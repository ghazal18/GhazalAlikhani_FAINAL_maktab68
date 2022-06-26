package com.example.myapplication.network

sealed class Resource<T>(val data: T? = null, val massage: String? = null, val code: String? = null) {
    class Error<T>(data: T? = null, massage: String = " ", code: String? = " "):Resource<T>(data,massage,code)
    class Loading<T> : Resource<T>()
    class Success<T>(data: T?) : Resource<T>(data)
}