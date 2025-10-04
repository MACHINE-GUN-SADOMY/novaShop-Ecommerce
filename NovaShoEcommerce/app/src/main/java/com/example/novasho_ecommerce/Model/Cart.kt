package com.example.novasho_ecommerce.Model

data class Cart (
    val items: MutableList<CartItem> = mutableListOf()
){
    val subTotal: Double get() = items.sumOf { it.product.price * it.quanty}

    val tax: Double get() = subTotal * 0.19

    val total: Double get() = subTotal + tax
}