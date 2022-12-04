package com.example.smb_p01

import androidx.lifecycle.LiveData

interface ProductDao {

    fun getProducts(): LiveData<List<Product>>
    fun getProductsCursor()
    fun getProductById(id: Long)
    fun insert(product: Product)
    fun update(product: Product)
    fun selectAll(): List<Product>
    fun delete(id: Long)
    fun deleteAll()
}
