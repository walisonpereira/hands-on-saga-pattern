package com.example.order.integration

import com.example.order.domain.Product
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class OrderPostRequest(
        @field:NotBlank val customerId: String,
        @field:NotEmpty val products: List<OrderProductPostRequest>
) {
    fun toProducts() = products.map { it.toProduct() }
}

data class OrderProductPostRequest(val id: String, val value: Double, val quantity: Int) {
    fun toProduct() = Product(id, value, quantity)
}