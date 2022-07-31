package com.example.order.integration

import com.example.customer.grpc.CustomerServiceGrpc
import com.example.stock.grpc.ProductServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration {

    @Bean
    fun customerChannel() = ManagedChannelBuilder
            .forAddress("localhost", 5000)
            .usePlaintext()
            .build()

    @Bean
    fun stockChannel() = ManagedChannelBuilder
            .forAddress("localhost", 5001)
            .usePlaintext()
            .build()

    @Bean
    fun customerServiceGrpc(customerChannel: ManagedChannel) = CustomerServiceGrpc.newBlockingStub(customerChannel)

    @Bean
    fun productServiceGrpc(stockChannel: ManagedChannel) = ProductServiceGrpc.newBlockingStub(stockChannel)

}