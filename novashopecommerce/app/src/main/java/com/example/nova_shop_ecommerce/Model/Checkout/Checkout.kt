package com.example.nova_shop_ecommerce.Model.Checkout

data class CheckoutForm(
    val direccion: String = "",
    val comuna: String = "",
    val ciudad: String = ""
) {
    fun toDireccionEnvio(): String {
        return "$direccion, $comuna, $ciudad"
    }
}