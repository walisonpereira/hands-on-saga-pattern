package com.example.customer.integration

import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.Nats
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class NatsConfiguration {

    @Bean
    fun connection(): Connection = Nats.connect()

    @Bean
    fun reserveCreditDispatcher(connection: Connection, messageHandler: ReserveCreditMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(messageHandler)
        return dispatcher.subscribe("customer-reserve-credit")
    }

    @Bean
    fun addCreditDispatcher(connection: Connection, messageHandler: AddCreditMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(messageHandler)
        return dispatcher.subscribe("customer-add-credit")
    }

}