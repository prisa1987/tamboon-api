package com.omise.api.entity

import com.google.gson.annotations.SerializedName

data class Donation(

        @SerializedName("customer_name")
        var name: String = "",

        @SerializedName("card_token")
        var token: String = "",

        @SerializedName("donation_amount")
        var amount: Double = 0.00
)