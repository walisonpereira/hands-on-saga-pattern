package com.example.stock.integration

import com.example.stock.domain.Product
import com.example.stock.domain.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/products")
class ProductResource(val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun create(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        logger.info("Receiving a POST /v1/products $request")
        val product = productService.create(request.name, request.value, request.quantity)
        val response = ProductResponse(product.id, product.name, product.value, product.quantity)
        logger.info("Received a POST /v1/products $response")
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieve() = productService.findAll()
}