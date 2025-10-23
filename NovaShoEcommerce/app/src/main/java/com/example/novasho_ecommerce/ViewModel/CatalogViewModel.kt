package com.example.novasho_ecommerce.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.novasho_ecommerce.Model.Product

class CatalogViewModel : ViewModel() {
    private val allProducts = listOf(
        Product(1, "Polera básica", "Ropa", 9990.0),
        Product(2, "Pantalón jogger", "Ropa", 14990.0),
        Product(3, "Polerón hoodie", "Ropa", 19990.0),
        Product(4, "Zapatillas urbanas", "Calzado", 24990.0),
        Product(5, "Chaqueta ligera", "Ropa", 29990.0)
    )

    var products by mutableStateOf(allProducts)
        private set

    var searchQuery by mutableStateOf("")
        private set

    fun onSearchChange(query: String) {
        searchQuery = query
        products = if (query.isBlank()) {
            allProducts
        } else {
            allProducts.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun getProductById(id: Int): Product? {
        return allProducts.find { it.id == id }
    }
}
