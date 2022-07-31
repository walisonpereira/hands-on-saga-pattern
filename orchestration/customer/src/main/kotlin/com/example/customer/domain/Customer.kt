package com.example.customer.domain

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Customer(val name: String, var creditLimit: Double) {

    @Id
    val id: String = UUID.randomUUID().toString()

    val createdAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
}
