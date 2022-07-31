package com.example.stock.domain

import com.example.stock.grpc.ProductServiceGrpcKt
import com.example.stock.grpc.ReserveProductRequest
import com.example.stock.grpc.ReserveProductResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(val repository: ProductRepository) : ProductServiceGrpcKt.ProductServiceCoroutineImplBase() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, value: Double, quantity: Int): Product {
        val customer = Product(name, value, quantity)
        repository.save(customer)
        logger.info("Created $customer")
        return customer
    }

    fun findAll(): Iterable<Product> = repository.findAll()

    override suspend fun reserveProduct(request: ReserveProductRequest): ReserveProductResponse {

        val product = repository.findByIdOrNull(request.productId)

        if (product == null) {
            logger.warn("Product ${request.productId} was not found")
            throw RuntimeException("Product ${request.productId} was not found")
        }

        val response = if (product.quantity >= request.quantity) {
            product.quantity -= request.quantity
            repository.save(product)
            ProductStatus.APPROVED.name
        }
        else {
            ProductStatus.DENIED.name
        }

        logger.info("Product ${request.productId} quantity was $response")

        return ReserveProductResponse.newBuilder()
                .setResponse(response)
                .build()
    }

}