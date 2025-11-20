package com.example.nova_shop_ecommerce.Model.Pedido

data class PedidoItemsRequest(
    val productoId: Long,
    val cantidad: Int,
    val precioUnitario: Double
)


data class PedidoRequest(
    val usuarioId: Long,
    val carritoId: Long,
    val direccionEnvio: String,
    val total: Double,
    val items: List<PedidoItemsRequest>
)

data class PedidoItemsResponse(
    val id: Long,
    val pedidoId: Long,
    val productoId: Long,
    val cantidad: Int,
    val precioUnitario: Double
)

data class PedidoResponse(
    val id: Long,
    val usuarioId: Long,
    val fecha: String?,
    val total: Double,
    val estado: String,
    val direccionEnvio: String,
    val creadoEn: String,
    val carritoId: Long,
    val items: List<PedidoItemsResponse>
)