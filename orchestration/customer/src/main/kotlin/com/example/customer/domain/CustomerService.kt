package com.example.customer.domain

import com.example.customer.grpc.*
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerService(val repository: CustomerRepository) : CustomerServiceGrpcKt.CustomerServiceCoroutineImplBase() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun create(name: String, creditLimit: Double): Customer {
        val customer = Customer(name, creditLimit)
        repository.save(customer)
        logger.info("Created $customer")
        return customer
    }

    fun findAll(): Iterable<Customer> = repository.findAll()

    override suspend fun reserveCredit(request: ReserveCreditRequest): ReserveCreditResponse {
        val customer = repository.findByIdOrNull(request.customerId)

        if (customer == null) {
            logger.warn("Customer ${request.customerId} was not found")
            return ReserveCreditResponse.newBuilder()
                    .setResponse(CustomerStatus.DENIED.name)
                    .build()
        }

        if (customer.creditLimit < request.value) {
            logger.warn("Customer ${request.customerId} limit ${request.value} was denied")
            return ReserveCreditResponse.newBuilder()
                    .setResponse(CustomerStatus.DENIED.name)
                    .build()
        }

        customer.creditLimit -= request.value
        repository.save(customer)
        logger.info("Customer ${request.customerId} limit ${request.value} was approved")

        return ReserveCreditResponse.newBuilder()
                .setResponse(CustomerStatus.APPROVED.name)
                .build()
    }

    override suspend fun addCredit(request: AddCreditRequest): AddCreditResponse {
        val customer = repository.findByIdOrNull(request.customerId)

        if (customer == null) {
            logger.warn("Customer ${request.customerId} was not found")
            return AddCreditResponse.newBuilder()
                    .setResponse(CustomerStatus.DENIED.name)
                    .build()
        }

        customer.creditLimit += request.value
        repository.save(customer)
        logger.warn("Customer ${request.customerId} limit ${request.value} was approved")

        return AddCreditResponse.newBuilder()
                .setResponse(CustomerStatus.APPROVED.name)
                .build()
    }

}