package com.omise.api.kotlin

import spark.Spark.get
import spark.Spark.port

class ServiceRunner() {

    fun run() {
        port(9011)
        get("/hello1", { req, res -> "Hello 11!!!"})
    }
}