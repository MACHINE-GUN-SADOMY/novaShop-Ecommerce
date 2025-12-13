package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.Producto.ProductoResponse
import com.example.nova_shop_ecommerce.ViewModel.ApiClient


class CatalogRepository {

    private val api = ApiClient.api

    suspend fun getProductos(): List<ProductoResponse> {
        return api.getProductos()
    }

    suspend fun getProducto(id: Long): ProductoResponse {
        return api.getProducto(id)
    }
}