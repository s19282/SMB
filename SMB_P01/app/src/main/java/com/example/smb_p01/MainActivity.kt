package com.example.smb_p01

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smb_p01.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                adapter.setProducts(it)
            }
        }
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.mapActivityButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
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
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_BACKGROUND_LOCATION"
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.POST_NOTIFICATIONS"
                ), 178)
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                ), 179)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
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