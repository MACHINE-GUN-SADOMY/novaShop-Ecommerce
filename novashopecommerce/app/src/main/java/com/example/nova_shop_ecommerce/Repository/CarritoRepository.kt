package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoItemRequest
import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoItemResponse
import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoResponse
import com.example.nova_shop_ecommerce.Model.CarritoModel.UpdateCantidadRequest
import com.example.nova_shop_ecommerce.ViewModel.ApiClient
import com.example.nova_shop_ecommerce.ViewModel.ApiService

class CarritoRepository {
    private val api = ApiClient.api

    suspend fun getCarrito(usuarioId: Long): CarritoResponse {
        return api.getCarrito(usuarioId)
    }

    suspend fun agregarItem(request: CarritoItemRequest): CarritoResponse {
        return api.agregarItemCarrito(request)
    }

    suspend fun modificarCantidad(itemId: Long, cantidad: Int): CarritoResponse {
        return api.modificarCantidadCarrito(itemId, UpdateCantidadRequest(cantidad))
    }

    suspend fun eliminarItem(itemId: Long): CarritoResponse {
        return api.eliminarItemCarrito(itemId)
    }

    suspend fun vaciarCarrito(carritoId: Long): CarritoResponse {
        return api.vaciarCarrito(carritoId)
    }
}
