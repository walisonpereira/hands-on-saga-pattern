package com.example.customer.domain

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerService(val repository: CustomerRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, creditLimit: Double): Customer {
        val customer = Customer(name, creditLimit)
        repository.save(customer)
        logger.info("Created $customer")
        return customer
    }

    fun findAll(): Iterable<Customer> = repository.findAll()

    fun reserveCredit(customerId: String, value: Double): CustomerStatus {
        val customer = repository.findByIdOrNull(customerId)

        if (customer == null) {
            logger.warn("Customer $customerId was not found")
            return CustomerStatus.DENIED
        }

        if (customer.creditLimit < value) {
            logger.warn("Customer $customerId limit $value was denied")
            return CustomerStatus.DENIED
        }

        customer.creditLimit -= value
        repository.save(customer)
        logger.info("Customer $customerId limit $value was approved")

        return CustomerStatus.APPROVED
    }

    fun addCredit(customerId: String, value: Double): CustomerStatus {
        val customer = repository.findByIdOrNull(customerId)

        if (customer == null) {
            logger.warn("Customer $customerId was not found")
            return CustomerStatus.DENIED
        }

        customer.creditLimit += value
        repository.save(customer)
        logger.warn("Customer $customerId limit $value was approved")

        return CustomerStatus.APPROVED
    }

}