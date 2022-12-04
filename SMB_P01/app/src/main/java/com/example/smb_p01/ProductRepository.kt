package com.example.smb_p01

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ProductRepository(private val firebaseDB: FirebaseDatabase) {
    val allProducts = MutableLiveData<HashMap<String, Product>>().also { it.value = HashMap() }
    init {
        firebaseDB.getReference("Product").addChildEventListener(
            object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val product = Product(
                        id = snapshot.ref.key as String,
                        name = snapshot.child("name").value as String,
                        price = snapshot.child("price").value as Double,
                        amount = snapshot.child("amount").value as Double,
                        isBought = snapshot.child("isBought").value as Boolean
                    )
                    allProducts.value?.put(product.id,product)
                    allProducts.postValue(allProducts.value)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val product = Product(
                        id = snapshot.ref.key as String,
                        name = snapshot.child("name").value as String,
                        price = snapshot.child("price").value as Double,
                        amount = snapshot.child("amount").value as Double,
                        isBought = snapshot.child("isBought").value as Boolean
                    )
                    allProducts.value?.put(product.id,product)
                    allProducts.postValue(allProducts.value)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    allProducts.value?.remove(snapshot.ref.key)
                    allProducts.postValue(allProducts.value)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("dbError",error.message)
                }
            }
        )
    }
    fun insert(product: Product) {
        firebaseDB.getReference("Product").push().also {
            product.id = it.ref.key.toString()
            it.setValue(product)
        }
    }

    fun update(product: Product) {
        val productRef = firebaseDB.getReference("Product/${product.id}")
        productRef.child("name").setValue(product.name)
        productRef.child("price").setValue(product.price)
        productRef.child("amount").setValue(product.amount)
        productRef.child("isBought").setValue(product.isBought)
    }

    fun delete(product: Product) = firebaseDB.getReference("Product/${product.id}")

    fun deleteAll() = firebaseDB.getReference("Product").removeValue()
}