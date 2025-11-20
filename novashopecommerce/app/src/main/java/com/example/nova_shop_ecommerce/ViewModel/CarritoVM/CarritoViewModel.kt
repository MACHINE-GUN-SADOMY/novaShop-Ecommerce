package com.example.nova_shop_ecommerce.ViewModel.CarritoVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoItemRequest
import com.example.nova_shop_ecommerce.Model.CarritoModel.CarritoResponse
import com.example.nova_shop_ecommerce.Repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CarritoState(
    val loading: Boolean = false,
    val carrito: CarritoResponse? = null,
    val error: String? = null
)

class CarritoViewModel : ViewModel() {

    private val repo = CarritoRepository()

    private val _carritoState = MutableStateFlow(CarritoState())
    val carritoState: StateFlow<CarritoState> = _carritoState

    fun cargarCarrito(usuarioId: Long) {
        viewModelScope.launch {
            try {
                _carritoState.value = _carritoState.value.copy(loading = true, error = null)
                val carrito = repo.getCarrito(usuarioId)
                _carritoState.value = CarritoState(loading = false, carrito = carrito, error = null)
            } catch (e: Exception) {
                _carritoState.value = CarritoState(loading = false, carrito = null, error = e.message)
            }
        }
    }

    fun agregarItem(usuarioId: Long, productoId: Long, cantidad: Int) {
        viewModelScope.launch {
            try {
                repo.agregarItem(CarritoItemRequest(usuarioId, productoId, cantidad))
                cargarCarrito(usuarioId)
            } catch (e: Exception) {
                _carritoState.value = _carritoState.value.copy(error = e.message)
            }
        }
    }

    fun modificarCantidad(itemId: Long, cantidad: Int, usuarioId: Long) {
        viewModelScope.launch {
            try {
                repo.modificarCantidad(itemId, cantidad)
                cargarCarrito(usuarioId)
            } catch (e: Exception) {
                _carritoState.value = _carritoState.value.copy(error = e.message)
            }
        }
    }

    fun eliminarItem(itemId: Long, usuarioId: Long) {
        viewModelScope.launch {
            try {
                repo.eliminarItem(itemId)
                cargarCarrito(usuarioId)
            } catch (e: Exception) {
                _carritoState.value = _carritoState.value.copy(error = e.message)
            }
        }
    }

    fun vaciarCarrito(carritoId: Long, usuarioId: Long) {
        viewModelScope.launch {
            try {
                repo.vaciarCarrito(carritoId)
                cargarCarrito(usuarioId)
            } catch (e: Exception) {
                _carritoState.value = _carritoState.value.copy(error = e.message)
            }
        }
    }

    // Helpers reales
    fun hasItems(): Boolean = _carritoState.value.carrito?.items?.isNotEmpty() == true
    fun getCarritoId(): Long = _carritoState.value.carrito?.carritoId ?: 0L
    fun getTotal(): Int = _carritoState.value.carrito?.total ?: 0
}