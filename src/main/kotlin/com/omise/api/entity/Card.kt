package com.omise.api.entity

data class Card(

        var id: String = "",
        var last_digits:String = "",
        var brand: String = "",
        var expiration_month: Int = 0,
        var expiration_year: Int = 0,
        var name: String = ""

)