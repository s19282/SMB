package com.example.smb_p01

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product): Long

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM Product;")
    fun selectAll(): List<Product>

    @Query("DELETE FROM Product WHERE product.id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM product")
    fun deleteAll()
}
