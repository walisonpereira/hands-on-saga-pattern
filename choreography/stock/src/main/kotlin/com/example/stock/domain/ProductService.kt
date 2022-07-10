package com.example.stock.domain

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(val repository: ProductRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, value: Double, quantity: Int): Product {
        val customer = Product(name, value, quantity)
        repository.save(customer)
        logger.info("Created $customer")
        return customer
    }

    fun findAll(): Iterable<Product> = repository.findAll()

    fun reserveProduct(productId: String, quantity: Int): ProductStatus {
        val product = repository.findByIdOrNull(productId)

        if (product == null) {
            logger.warn("Product $productId was not found")
            return ProductStatus.DENIED
        }

        if (product.quantity < quantity) {
            logger.warn("Product $productId quantity was DENIED")
            return ProductStatus.DENIED
        }

        product.quantity -= quantity
        repository.save(product)
        logger.info("Product $productId quantity was APPROVED")

        return ProductStatus.APPROVED
    }
}