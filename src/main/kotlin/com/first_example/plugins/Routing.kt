package com.first_example.plugins

import com.first_example.model.Product
import com.first_example.persistence.ProductDatabase
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respondText("Hello Ktor!!!!")
        }

        get("/products") {
//            call.respond(IN_MEMORY_PRODUCTS)
            call.respond(ProductDatabase.dao.products())
        }

        get("/product") {
            val upc = call.request.queryParameters["upc"]?.toInt()

            if (upc == null) {
                call.respondText("Missing upc", status = HttpStatusCode.BadRequest)
                return@get
            }

            when (val product = ProductDatabase.dao.product(upc)) {
                null -> call.respond(HttpStatusCode.OK)
                else -> call.respond(product)
            }
        }

        authenticate {
            post("/add-product") {
                val body = call.receive<Product>()

                when(val insertedProduct = ProductDatabase.dao.addProduct(body)) {
                    null -> call.respondText("Failed to add product: $body", status = HttpStatusCode.InternalServerError)
                    else -> call.respondText("Added product: $body", status = HttpStatusCode.Created)
                }

            }
        }
    }
}
