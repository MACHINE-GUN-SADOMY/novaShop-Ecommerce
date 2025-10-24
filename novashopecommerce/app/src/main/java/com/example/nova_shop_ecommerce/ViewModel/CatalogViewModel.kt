package com.example.nova_shop_ecommerce.viewmodel


import androidx.lifecycle.ViewModel
import com.example.nova_shop_ecommerce.Model.Product
import com.example.nova_shop_ecommerce.Repository.CatalogRepository
import kotlinx.coroutines.flow.StateFlow

class CatalogViewModel : ViewModel() {

    private val repository = CatalogRepository()
    val products: StateFlow<List<Product>> get() = repository.products

    fun onSearchChange(query: String) {
        repository.search(query)
    }

    fun getProductById(id: String): Product? {
        return repository.getProductById(id)
    }
}
