package com.example.nova_shop_ecommerce.Model.CarritoModel

data class CarritoResponse(
    val carritoId: Long,
    val usuarioId: Long,
    val estado: String,
    val items: List<CarritoItemResponse>,
    val total: Int
)

data class CarritoItemResponse(
    val itemId: Long,
    val productoId: Long,
    val nombreProducto: String,
    val cantidad: Int,
    val precioUnitario: Int,
    val subtotal: Int
)

data class CarritoItemRequest(
    val usuarioId: Long,
    val productoId: Long,
    val cantidad: Int
)

data class UpdateCantidadRequest(
    val cantidad: Int
)