package com.example.novasho_ecommerce.Model

data class Order (
    val id: Int,
    val items: List<CartItem>,
    val total: Int,
    val createdAt: Long,
    val status: String,
    val subtotal: Double,
    val tax: Double
)