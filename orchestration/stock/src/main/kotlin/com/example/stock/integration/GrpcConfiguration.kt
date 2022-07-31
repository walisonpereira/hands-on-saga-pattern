package com.example.stock.integration

import com.example.stock.domain.ProductService
import io.grpc.ServerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun start(productService: ProductService) = ServerBuilder.forPort(5001)
            .addService(productService)
            .build()
            .start()

}