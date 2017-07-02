package com.omise.api

import co.omise.Client
import co.omise.models.Charge
import co.omise.models.OmiseError
import co.omise.models.Token
import com.google.gson.Gson
import com.omise.api.entity.Card
import com.omise.api.entity.Donation
import com.omise.api.entity.User
import spark.Request
import spark.Response
import spark.Spark.*
import java.io.File
import java.io.InputStream

class ServiceRunner {

    fun run() {
        port(9000)
        val client = Client("pkey_test_58hk7ucyurqzpyosw3p", "skey_test_58hmsoc5htftzctsrvb")
        getUser(client)
        getCharities()
        donateToCharity(client)

        internalServerError { request, response ->
            response.type("application/json")
            response.status(404)
            "{\"message\":\"Sorry, something went wrong\"}"
        }
    }

    fun getUser(client: Client) {
        get("/user", { _, _ ->
            val tokenCreate = Token.Create()
                    .name("JOHN DOE")
                    .number("4242424242424242")
                    .postalCode("10320")
                    .expirationMonth(7)
                    .expirationYear(2019)
            val token = client.tokens().create(tokenCreate)

            val user = User().apply {
                tokenId = token.id
                card = Card().apply {
                    id = token.card.id
                    last_digits = token.card.lastDigits
                    brand = token.card.brand
                    expiration_month = token.card.expirationMonth
                    expiration_year = token.card.expirationYear
                    name = token.card.name
                }
            }
            Gson().toJson(user)
        })
    }

    fun getCharities() {
        get("/charities") { _, _ ->
            val inputStream: InputStream = File("src/main/kotlin/com/omise/api/charity.json").inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            inputString
        }
    }

    fun donateToCharity(client: Client) {
        post("/charities/:id/donate") { request: Request, response: Response ->
            try {
                val charityId = request.params("id")
                val donation = Gson().fromJson(request.body(), Donation::class.java)
                val createCharge = Charge.Create()
                        .amount((donation.amount * 100).toLong()) // convert baht to satang unit
                        .currency("thb")
                        .card(donation.token)

                response.type("application/json")
                Gson().toJson(client.charges().create(createCharge))
            } catch (e: OmiseError) {
                response.type("application/json")
                response.status(500)
                "{\"message\":\"${e.message}\"}"
            }
        }
    }

}