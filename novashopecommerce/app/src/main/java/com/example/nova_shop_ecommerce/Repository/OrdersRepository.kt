package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.CrearPedidoRequest
import com.example.nova_shop_ecommerce.Model.PedidoResponse
import com.example.nova_shop_ecommerce.ViewModel.ApiClient


class OrdersRepository {
    private val api = ApiClient.api

    suspend fun getOrder(orderId: Long): PedidoResponse {
        return api.getPedido(orderId)
    }

    suspend fun getOrdersByUser(userId: Long): List<PedidoResponse> {
        return api.getPedidosUsuario(userId)
    }

    suspend fun createOrder(
        userId: Long,
        cartId: Long,
        address: String
    ): PedidoResponse {
        val request = CrearPedidoRequest(
            usuarioId = userId,
            carritoId = cartId,
            direccionEnvio = address
        )
        return api.crearPedido(request)
    }

    suspend fun payOrder(orderId: Long): PedidoResponse {
        return api.pagarPedido(orderId)
    }
}

