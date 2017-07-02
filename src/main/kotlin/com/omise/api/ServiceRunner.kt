package com.omise.api

import spark.Spark.get
import spark.Spark.port
import java.io.File
import java.io.InputStream

class ServiceRunner {

    fun run() {
        val inputStream: InputStream = File("src/main/kotlin/com/omise/api/charity.json").inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        println(inputString)

        port(9000)
        get("/charities", { req, res -> inputString})
    }
}