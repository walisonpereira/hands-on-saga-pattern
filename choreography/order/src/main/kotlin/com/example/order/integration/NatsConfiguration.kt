package com.example.order.integration

import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfiguration {
    
    @Bean
    fun connection(): Connection = Nats.connect()
}