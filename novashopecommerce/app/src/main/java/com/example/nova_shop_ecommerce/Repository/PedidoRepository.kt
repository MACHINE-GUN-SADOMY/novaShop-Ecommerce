package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.CrearPedidoRequest
import com.example.nova_shop_ecommerce.Model.PedidoResponse
import com.example.nova_shop_ecommerce.ViewModel.ApiClient

class PedidoRepository {
    private val api = ApiClient.api

    suspend fun getPedido(pedidoId: Long): PedidoResponse {
        return api.getPedido(pedidoId)
    }

    suspend fun getPedidosUsuario(usuarioId: Long): List<PedidoResponse> {
        return api.getPedidosUsuario(usuarioId)
    }

    suspend fun crearPedido(
        usuarioId: Long,
        carritoId: Long,
        direccion: String
    ): PedidoResponse {
        val req = CrearPedidoRequest(
            usuarioId = usuarioId,
            carritoId = carritoId,
            direccionEnvio = direccion
        )
        return api.crearPedido(req)
    }

    suspend fun pagarPedido(pedidoId: Long): PedidoResponse {
        return api.pagarPedido(pedidoId)
    }
}