package com.example.nova_shop_ecommerce.Model.Producto

data class ProductoRequest(
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
    val categoriaId: Long
)

data class ProductoResponse(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
    val categoriaId: Long
)