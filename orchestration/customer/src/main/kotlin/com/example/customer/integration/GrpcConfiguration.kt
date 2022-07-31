package com.example.customer.integration

import com.example.customer.domain.CustomerService
import io.grpc.ServerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun start(customerService: CustomerService) = ServerBuilder.forPort(5000)
            .addService(customerService)
            .build()
            .start()

}