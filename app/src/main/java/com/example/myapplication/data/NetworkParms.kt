package com.example.myapplication.data

class NetworkParams {
    companion object {
        const val CONSUMER_KEY = "ck_1638823b5544cd50791d0703153dccdf48c82e8c"
        const val CONSUMER_SECRET = "cs_dd772c1e5ac603602694fd19601a830c05c1deb3"
        const val ORDER_BY_RATING = "rating"
        const val ORDER_BY_DATE = "date"
        const val ORDER_BY_POPULARITY = "popularity"
        const val ORDER_BY_PRICE = "price"
        const val DESC_ORDER = "desc"
        const val ASC_ORDER = "asc"
    }
}

enum class Status {
    Loading,
    Failed,
    Successful
}