package com.example.customer.integration

import com.example.customer.domain.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/customers")
class CustomerResource(val customerService: CustomerService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun create(@RequestBody request: CustomerPostRequest): ResponseEntity<CustomerResponse> {
        logger.info("Receiving a POST /v1/customers $request")
        val order = customerService.create(request.name, request.creditLimit)
        val response = CustomerResponse(order.id, order.name, order.creditLimit)
        logger.info("Received a POST /v1/customers $response")
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieve() = customerService.findAll()
}