package com.example.novasho_ecommerce.Model

data class Order (
    val id: Int,
    val date: String,
    val items: List<CartItem>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val status: String
)