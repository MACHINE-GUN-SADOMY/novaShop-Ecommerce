package com.example.nova_shop_ecommerce.Model

data class CrearPedidoRequest(
    val usuarioId: Long,
    val carritoId: Long,
    val direccionEnvio: String
)

data class PedidoResponse(
    val id: Long,
    val usuarioId: Long,
    val carritoId: Long,
    val estado: String,
    val direccionEnvio: String,
    val fechaCreacion: String,
    val total: Int,
    val items: List<PedidoItemResponse>
)

data class PedidoItemResponse(
    val id: Long,
    val productoId: Long,
    val nombreProducto: String,
    val cantidad: Int,
    val precioUnitario: Int,
    val subtotal: Int
)