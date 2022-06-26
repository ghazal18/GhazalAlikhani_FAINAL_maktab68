package com.example.myapplication.network

fun errorCode(errorCode: String): String {
    var error = when (errorCode) {
        "400" -> "bad request"
        "401" -> "unauthorized"
        "404" -> "Not Found"
        "500" -> "Internal server error"
        "504" -> " Gateway timeOut"
        else -> " unknown error"
    }
    return error
}