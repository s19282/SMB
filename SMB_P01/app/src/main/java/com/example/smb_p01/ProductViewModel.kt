package com.example.smb_p01

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    val allProducts: MutableLiveData<HashMap<String, Product>>
    var firebaseDB: FirebaseDatabase = FirebaseDatabase.getInstance()

    init {
        repository = ProductRepository(firebaseDB)
        allProducts = repository.allProducts
    }

    fun insert(product: Product) = repository.insert(product)

    fun update(product: Product) = repository.update(product)

    fun delete(product: Product) = repository.delete(product)

    fun deleteAll() = repository.deleteAll()
}