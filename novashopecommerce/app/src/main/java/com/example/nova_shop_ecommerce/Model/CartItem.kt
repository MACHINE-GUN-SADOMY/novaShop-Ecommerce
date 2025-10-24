package com.example.nova_shop_ecommerce.Model

data class CartItem(
    val productId: String,
    val product: Product,
    val quanty: Int
)