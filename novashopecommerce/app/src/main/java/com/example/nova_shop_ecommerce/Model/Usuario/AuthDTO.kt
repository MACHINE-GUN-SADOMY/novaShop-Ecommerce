package com.example.nova_shop_ecommerce.Model.Usuario

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String? = null,
    val user: UsuarioResponse? = null
)

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)

data class UsuarioResponse(
    val id: Long,
    val nombre: String,
    val email: String,
    val rol: String
)