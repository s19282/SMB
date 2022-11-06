package com.example.smb_p01

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    private val allProducts: LiveData<List<Product>>

    init {
        repository = ProductRepository(ProductDB.getDB(application).product)
        allProducts = repository.allProducts
    }

    suspend fun insert(product: Product) = repository.insert(product)

    suspend fun update(product: Product) = repository.update(product)

    suspend fun delete(id: Long) = repository.delete(id)

    suspend fun deleteAll() = repository.deleteAll()
}