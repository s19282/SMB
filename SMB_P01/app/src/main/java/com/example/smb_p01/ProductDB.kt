package com.example.smb_p01

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Product::class],
    version = 1
)
abstract class ProductDB : RoomDatabase() {
    abstract val product: ProductDao

    companion object {
        fun getDB(context: Context) = Room.databaseBuilder(
            context,
            ProductDB::class.java,
            "Product's database"
        ).build()
    }
}