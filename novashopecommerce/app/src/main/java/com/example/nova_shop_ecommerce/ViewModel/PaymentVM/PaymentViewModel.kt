package com.example.nova_shop_ecommerce.ViewModel.PaymentVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Model.PedidoResponse
import com.example.nova_shop_ecommerce.Repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PaymentState(
    val loading: Boolean = false,
    val error: String? = null,
    val pedido: PedidoResponse? = null,
    val direccion: String = "",
    val comuna: String = "",
    val ciudad: String = ""
)

class PaymentViewModel : ViewModel() {

    private val repo = PedidoRepository()

    private val _state = MutableStateFlow(PaymentState())
    val state: StateFlow<PaymentState> = _state

    fun actualizarDireccion(valor: String) {
        _state.value = _state.value.copy(direccion = valor)
    }

    fun actualizarComuna(valor: String) {
        _state.value = _state.value.copy(comuna = valor)
    }

    fun actualizarCiudad(valor: String) {
        _state.value = _state.value.copy(ciudad = valor)
    }

    fun pagar(usuarioId: Long, carritoId: Long) {
        val current = _state.value
        val direccionEnvio = "${current.direccion}, ${current.comuna}, ${current.ciudad}"

        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val creado = repo.crearPedido(
                    usuarioId = usuarioId,
                    carritoId = carritoId,
                    direccion = direccionEnvio
                )

                val pagado = repo.pagarPedido(creado.id)

                _state.value = _state.value.copy(
                    loading = false,
                    pedido = pagado
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Error al procesar el pago"
                )
            }
        }
    }
}