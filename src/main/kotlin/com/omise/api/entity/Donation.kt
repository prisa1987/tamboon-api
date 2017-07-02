package com.omise.api.entity

import com.google.gson.annotations.SerializedName

data class Donation(

        @com.google.gson.annotations.SerializedName("customer_name")
        var name: String = "",

        @com.google.gson.annotations.SerializedName("card_token")
        var token: String = "",

        @com.google.gson.annotations.SerializedName("donation_amount")
        var amount: Double = 0.00
)