package com.example.customer.integration

import com.example.customer.domain.CustomerService
import com.fasterxml.jackson.databind.ObjectMapper
import io.nats.client.Connection
import io.nats.client.Message
import io.nats.client.MessageHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ReserveCreditMessageHandler(
        val customerService: CustomerService,
        val objectMapper: ObjectMapper,
        val connection: Connection
) : MessageHandler {

    override fun onMessage(message: Message?) {
        if (message != null) {
            val json = String(message.data, StandardCharsets.UTF_8)
            val reserveCreditRequest = objectMapper.readValue(json, ReserveCreditRequest::class.java)
            val reserveCreditResponse = customerService.reserveCredit(reserveCreditRequest.customerId, reserveCreditRequest.value)
            connection.publish(message.replyTo, message.subject, objectMapper.writeValueAsBytes(reserveCreditResponse))
        }
    }

}