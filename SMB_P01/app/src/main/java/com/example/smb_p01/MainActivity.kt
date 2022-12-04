package com.example.smb_p01

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smb_p01.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SharedPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val pvm = ProductViewModel(application)
            val adapter = ProductAdapter(pvm)

            binding.productList.layoutManager = LinearLayoutManager(this)
            binding.productList.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
            binding.productList.adapter = adapter
            pvm.allProducts.observe(this) {
                it.let {
                    adapter.setProducts(it.values.toList())
                }
            }
            binding.modeSwitch.setOnClickListener {
                if (binding.modeSwitch.isChecked) {
                    adapter.switchMode("users/${auth.currentUser?.uid}/products")
                } else {
                    adapter.switchMode("sharedProducts")
                }
            }

            binding.logoutbutton.setOnClickListener {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }

            binding.settingsButton.setOnClickListener {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

            binding.addProductButton.setOnClickListener {
                val intent = Intent(binding.root.context, ProductActivity::class.java)
                intent.putExtra("mode", "Add")
                ContextCompat.startActivity(
                    binding.root.context,
                    intent,
                    Bundle()
                )
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.coordinatorLayout.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "backgroundColor",
                    "#50F1A0"
                )
            )
        )
        binding.mainText.textSize = sp.getString("fontSize", "20")!!.toFloat()
    }
}