package com.example.novasho_ecommerce.Model

data class CartItem(
    val productId: String,
    val product: Product,
    val quanty: Int
)