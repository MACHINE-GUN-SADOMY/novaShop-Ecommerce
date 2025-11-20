package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.Usuario.LoginRequest
import com.example.nova_shop_ecommerce.Model.Usuario.LoginResponse
import com.example.nova_shop_ecommerce.Model.Usuario.RegisterRequest
import com.example.nova_shop_ecommerce.Model.Usuario.UsuarioResponse
import com.example.nova_shop_ecommerce.ViewModel.ApiClient


class AuthRepository {
    private val api = ApiClient.api

    suspend fun login(email: String, password: String): LoginResponse {
        return api.login(LoginRequest(email, password))
    }

    suspend fun register(nombre: String, email: String, password: String): UsuarioResponse {
        return api.register(RegisterRequest(nombre, email, password))
    }

    suspend fun getUser(id: Long): UsuarioResponse {
        return api.getUser(id)
    }
}
