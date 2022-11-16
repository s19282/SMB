package com.example.smb_p01

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM Product")
    fun getProductsCursor(): Cursor

    @Query("SELECT * FROM Product WHERE id=:id ")
    fun getProductById(id: Long): Cursor

    @Insert
    fun insert(product: Product): Long

    @Update
    fun update(product: Product): Int

    @Query("SELECT * FROM Product;")
    fun selectAll(): List<Product>

    @Query("DELETE FROM Product WHERE product.id = :id")
    fun delete(id: Long): Int

    @Query("DELETE FROM product")
    fun deleteAll(): Int
}
