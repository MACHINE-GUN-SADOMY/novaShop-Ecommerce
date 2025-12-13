package com.example.nova_shop_ecommerce.ViewModel.CatalogVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Model.Producto.ProductoResponse
import com.example.nova_shop_ecommerce.Repository.CatalogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CatalogState(
    val loading: Boolean = false,
    val productos: List<ProductoResponse> = emptyList(),
    val producto: ProductoResponse? = null,
    val error: String? = null
)

class CatalogViewModel : ViewModel() {

    private val repo = CatalogRepository()

    private val _catalogState = MutableStateFlow(CatalogState())
    val catalogState: StateFlow<CatalogState> = _catalogState

    fun loadProductos() {
        viewModelScope.launch {
            _catalogState.value = _catalogState.value.copy(loading = true, error = null)
            try {
                val productos = repo.getProductos()
                _catalogState.value = CatalogState(productos = productos)
            } catch (e: Exception) {
                _catalogState.value = CatalogState(
                    error = e.message ?: "Error cargando productos"
                )
            }
        }
    }

    fun loadProducto(id: Long) {
        viewModelScope.launch {
            _catalogState.value = _catalogState.value.copy(loading = true, error = null)
            try {
                val producto = repo.getProducto(id)
                _catalogState.value = CatalogState(producto = producto)
            } catch (e: Exception) {
                _catalogState.value = CatalogState(
                    error = e.message ?: "Error cargando producto"
                )
            }
        }
    }
}
