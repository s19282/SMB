package com.example.smb_p01

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smb_p01.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        binding.addProductButton.setOnClickListener {
            val intent = Intent(binding.root.context, ProductActivity::class.java)
            intent.putExtra("mode", "Add")
            ContextCompat.startActivity(
                binding.root.context,
                intent,
                Bundle()
            )
        }
    }


}