package com.example.order.domain

import com.example.customer.grpc.AddCreditRequest
import com.example.customer.grpc.CustomerServiceGrpc
import com.example.customer.grpc.ReserveCreditRequest
import com.example.stock.grpc.ProductServiceGrpc
import com.example.stock.grpc.ReserveProductRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository,
                   val customerService: CustomerServiceGrpc.CustomerServiceBlockingStub,
                   val productService: ProductServiceGrpc.ProductServiceBlockingStub) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun create(customerId: String, products: List<Product>): Order {

        val order = Order(customerId, OrderStatus.PENDING, products)
        orderRepository.save(order)
        logger.info("Created $order")

        val reserveCredit = customerService.reserveCredit(ReserveCreditRequest.newBuilder()
                .setCustomerId(order.customerId)
                .setValue(order.getValue())
                .build())
        val customerStatus = CustomerStatus.valueOf(reserveCredit.response)

        var productStatus = ProductStatus.APPROVED
        for (product in order.products) {
            val reserveProduct = productService.reserveProduct(ReserveProductRequest.newBuilder()
                    .setProductId(product.id)
                    .setQuantity(product.quantity)
                    .build())
            productStatus = ProductStatus.valueOf(reserveProduct.response)
            if (ProductStatus.DENIED == productStatus) {
                customerService.addCredit(AddCreditRequest.newBuilder()
                        .setCustomerId(order.customerId)
                        .setValue(order.getValue())
                        .build())
                break
            }
        }

        order.status = if (CustomerStatus.APPROVED == customerStatus && ProductStatus.APPROVED == productStatus) OrderStatus.APPROVED else OrderStatus.DENIED
        orderRepository.save(order)

        return order
    }

    fun getAll(): Iterable<Order> = orderRepository.findAll()

}