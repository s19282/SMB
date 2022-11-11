package com.example.smb_p01

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        repository = ProductRepository(ProductDB.getDB(application).product)
        allProducts = repository.allProducts
    }

    fun insert(product: Product) = repository.insert(product)

    fun update(product: Product) = repository.update(product)

    fun delete(id: Long) = repository.delete(id)

    fun deleteAll() = repository.deleteAll()
}