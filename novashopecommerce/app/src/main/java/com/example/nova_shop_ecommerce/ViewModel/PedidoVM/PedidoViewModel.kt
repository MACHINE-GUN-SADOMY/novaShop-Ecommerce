package com.example.nova_shop_ecommerce.ViewModel.PedidoVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Model.Order.PedidoResponse
import com.example.nova_shop_ecommerce.Repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PedidoViewModel : ViewModel() {

    private val repo = PedidoRepository()

    var pedidoState = MutableStateFlow(PedidoState())
        private set

    fun crearPedido(usuarioId: Long, carritoId: Long, direccion: String) {
        viewModelScope.launch {
            try {
                pedidoState.value = pedidoState.value.copy(loading = true)
                val pedido = repo.crearPedido(usuarioId, carritoId, direccion)
                pedidoState.value = PedidoState(pedido = pedido)
            } catch (e: Exception) {
                pedidoState.value = PedidoState(error = e.message)
            }
        }
    }

    fun obtenerPedido(id: Long) {
        viewModelScope.launch {
            try {
                pedidoState.value = pedidoState.value.copy(loading = true)
                val pedido = repo.getPedido(id)
                pedidoState.value = PedidoState(pedido = pedido)
            } catch (e: Exception) {
                pedidoState.value = PedidoState(error = e.message)
            }
        }
    }

    fun obtenerPedidosUsuario(usuarioId: Long) {
        viewModelScope.launch {
            try {
                pedidoState.value = pedidoState.value.copy(loading = true)
                val pedidos = repo.getPedidosUsuario(usuarioId)
                pedidoState.value = PedidoState(listaPedidos = pedidos)
            } catch (e: Exception) {
                pedidoState.value = PedidoState(error = e.message)
            }
        }
    }

    fun pagarPedido(id: Long) {
        viewModelScope.launch {
            try {
                pedidoState.value = pedidoState.value.copy(loading = true)
                val pedido = repo.pagarPedido(id)
                pedidoState.value = PedidoState(pedido = pedido)
            } catch (e: Exception) {
                pedidoState.value = PedidoState(error = e.message)
            }
        }
    }
}

data class PedidoState(
    val loading: Boolean = false,
    val pedido: PedidoResponse? = null,
    val listaPedidos: List<PedidoResponse> = emptyList(),
    val error: String? = null
)
