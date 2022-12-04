package com.example.smb_p01

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlin.collections.HashMap

class ProductRepository(private val firebaseDB: FirebaseDatabase) {
    private var auth: FirebaseAuth = Firebase.auth
    private var path = "users/${auth.currentUser?.uid}/products"
    val allProducts = MutableLiveData<HashMap<String, Product>>().also { it.value = HashMap() }

    init {
        firebaseDB.getReference(path).addChildEventListener(
            object : ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val price: Double = try {
                        snapshot.child("price").value as Double
                    } catch (exception: ClassCastException){
                        (snapshot.child("price").value as Long).toDouble()
                    }
                    val amount: Double = try {
                        snapshot.child("amount").value as Double
                    } catch (exception: ClassCastException){
                        (snapshot.child("amount").value as Long).toDouble()
                    }
                    val product = Product(
                        id = snapshot.ref.key as String,
                        name = snapshot.child("name").value as String,
                        price = price,
                        amount = amount,
                        isBought = snapshot.child("bought").value as Boolean
                    )
                    allProducts.value?.put(product.id,product)
                    allProducts.postValue(allProducts.value)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    onChildAdded(snapshot,previousChildName)
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
        firebaseDB.getReference(path).push().also {
            product.id = it.ref.key.toString()
            it.setValue(product)
        }
    }

    fun update(product: Product) {
        val productRef = firebaseDB.getReference("$path/${product.id}")
        productRef.child("name").setValue(product.name)
        productRef.child("price").setValue(product.price)
        productRef.child("amount").setValue(product.amount)
        productRef.child("isBought").setValue(product.isBought)
    }

    fun delete(product: Product) = firebaseDB
        .getReference("$path/${product.id}")
        .removeValue()

    fun deleteAll() = firebaseDB.getReference(path).removeValue()
}