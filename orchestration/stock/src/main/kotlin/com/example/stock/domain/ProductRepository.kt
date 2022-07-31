package com.example.stock.domain

import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, String>