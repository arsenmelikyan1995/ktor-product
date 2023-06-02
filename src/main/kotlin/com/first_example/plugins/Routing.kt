package com.first_example.plugins

import com.first_example.model.IN_MEMORY_PRODUCTS
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respondText("Hello Ktor!!!")
        }

        get("/products") {
//            call.respondText("A list of products")
            call.respond(IN_MEMORY_PRODUCTS)
        }
    }
}
