package com.example.nova_shop_ecommerce.Model

data class ProductoResponse(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
    val categoriaId: Long
)