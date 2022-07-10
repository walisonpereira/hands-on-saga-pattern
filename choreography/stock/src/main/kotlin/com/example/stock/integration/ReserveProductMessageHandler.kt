package com.example.stock.integration

import com.example.stock.domain.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import io.nats.client.Connection
import io.nats.client.Message
import io.nats.client.MessageHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ReserveProductMessageHandler(
        val productService: ProductService,
        val objectMapper: ObjectMapper,
        val connection: Connection
) : MessageHandler {

    override fun onMessage(message: Message?) {
        if (message != null) {
            val json = String(message.data, StandardCharsets.UTF_8)
            val reserveProductRequest = objectMapper.readValue(json, ReserveProductRequest::class.java)
            val reserveProductResponse = productService.reserveProduct(reserveProductRequest.productId, reserveProductRequest.quantity)
            connection.publish(message.replyTo, message.subject, objectMapper.writeValueAsBytes(reserveProductResponse))
        }
    }

}
