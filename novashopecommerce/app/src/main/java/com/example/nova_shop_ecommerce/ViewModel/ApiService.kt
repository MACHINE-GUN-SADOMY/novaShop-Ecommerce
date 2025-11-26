package com.example.nova_shop_ecommerce.ViewModel

import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoItemRequest
import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoResponse
import com.example.nova_shop_ecommerce.Model.CarritoModel.UpdateCantidadRequest
import com.example.nova_shop_ecommerce.Model.Order.CrearPedidoRequest
import com.example.nova_shop_ecommerce.Model.Order.PedidoResponse
import com.example.nova_shop_ecommerce.Model.ProductoResponse
import com.example.nova_shop_ecommerce.Model.Usuario.LoginRequest
import com.example.nova_shop_ecommerce.Model.Usuario.LoginResponse
import com.example.nova_shop_ecommerce.Model.Usuario.RegisterRequest
import com.example.nova_shop_ecommerce.Model.Usuario.UsuarioResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // ---------------------------
    //          AUTH
    // ---------------------------
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): UsuarioResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("auth/{id}")
    suspend fun getUser(@Path("id") id: Long): UsuarioResponse

    // ---------------------------
    //        PRODUCTOS
    // ---------------------------
    @GET("productos")
    suspend fun getProductos(): List<ProductoResponse>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Long): ProductoResponse


    // ---------------------------
    //          CARRITO
    // ---------------------------
    @GET("carrito/{usuarioId}")
    suspend fun getCarrito(@Path("usuarioId") usuarioId: Long): CarritoResponse

    @POST("carrito/items")
    suspend fun agregarItemCarrito(@Body request: CarritoItemRequest): CarritoResponse

    @PUT("carrito/items/{itemId}")
    suspend fun modificarCantidadCarrito(
        @Path("itemId") itemId: Long,
        @Body request: UpdateCantidadRequest
    ): CarritoResponse

    @DELETE("carrito/items/{itemId}")
    suspend fun eliminarItemCarrito(@Path("itemId") itemId: Long): CarritoResponse

    @DELETE("carrito/{carritoId}")
    suspend fun vaciarCarrito(@Path("carritoId") carritoId: Long): CarritoResponse

    // ---------------------------
    //          PEDIDOS
    // ---------------------------

    @GET("pedido/{id}")
    suspend fun getPedido(@Path("id") pedidoId: Long): PedidoResponse

    @GET("pedido/usuario/{id}")
    suspend fun getPedidosUsuario(@Path("id") usuarioId: Long): List<PedidoResponse>

    @POST("pedido")
    suspend fun crearPedido(@Body request: CrearPedidoRequest): PedidoResponse

    @POST("pedido/{id}/pagar")
    suspend fun pagarPedido(@Path("id") pedidoId: Long): PedidoResponse
}
