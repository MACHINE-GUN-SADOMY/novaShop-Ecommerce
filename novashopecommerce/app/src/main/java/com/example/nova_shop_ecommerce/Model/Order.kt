package com.example.nova_shop_ecommerce.Model

data class Order (
    val id: Int,
    val items: List<CartItem>,
    val total: Double,
    val createdAt: Long,
    val status: String,
    val subtotal: Double,
    val tax: Double,
    val shipping: ShippingInfo
)