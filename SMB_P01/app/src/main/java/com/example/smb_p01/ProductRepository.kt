package com.example.smb_p01

class ProductRepository(private val productDao: ProductDao) {
    val allProducts = productDao.getProducts()

    fun insert(product: Product) = productDao.insert(product)

    fun update(product: Product) = productDao.update(product)

    fun delete(id: Long) = productDao.delete(id)

    fun deleteAll() = productDao.deleteAll()
}