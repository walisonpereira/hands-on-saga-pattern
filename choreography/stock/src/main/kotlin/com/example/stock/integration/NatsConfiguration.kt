package com.example.stock.integration

import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.Nats
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfiguration {

    @Bean
    fun connection(): Connection = Nats.connect()

    @Bean
    fun reserveProductDispatcher(connection: Connection, messageHandler: ReserveProductMessageHandler): Dispatcher {
        val dispatcher = connection.createDispatcher(messageHandler)
        return dispatcher.subscribe("stock-reserve-product")
    }

}