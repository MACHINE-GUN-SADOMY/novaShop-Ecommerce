package com.example.nova_shop_ecommerce.Repository

import com.example.nova_shop_ecommerce.Model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogRepository {

    private val allProducts = listOf(
        Product(1, "Polera básica", "Ropa", 9990.0),
        Product(2, "Pantalón jogger", "Ropa", 14990.0),
        Product(3, "Polerón hoodie", "Ropa", 19990.0),
        Product(4, "Zapatillas urbanas", "Calzado", 24990.0),
        Product(5, "Chaqueta ligera", "Ropa", 29990.0)
    )

    private val _products = MutableStateFlow(allProducts)
    val products: StateFlow<List<Product>> get() = _products

    fun search(query: String) {
        _products.value = if (query.isBlank()) {
            allProducts
        } else {
            allProducts.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun getProductById(id: String): Product? {
        return allProducts.find { it.id.toString() == id || it.id == id.toIntOrNull() }
    }
}
