package com.example.stock.domain

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Product(val name: String, var value: Double, var quantity: Int) {

    @Id
    val id: String = UUID.randomUUID().toString()

    val createdAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
}