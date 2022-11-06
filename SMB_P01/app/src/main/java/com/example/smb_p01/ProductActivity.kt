package com.example.smb_p01

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.smb_p01.databinding.ActivityProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pvm = ProductViewModel(application)
        val adapter = ProductAdapter(pvm)
        binding.topText.text = intent.getStringExtra("mode")+ " product"
        binding.editTextTextProductName.setText(intent.getStringExtra("name"))
        binding.editTextTextProductAmount.setText(intent.getStringExtra("amount"))
        binding.editTextTextProductPrice.setText(intent.getStringExtra("price"))
        binding.checkBoxIsBought.text = intent.getStringExtra("isBought")

        binding.rejectAndCloseButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.addButton.setOnClickListener {
            CoroutineScope(IO).launch {
                adapter.add(
                    Product(
                        name = binding.editTextTextProductName.text.toString(),
                        price = binding.editTextTextProductPrice.text.toString().toDouble(),
                        amount = binding.editTextTextProductAmount.text.toString().toDouble(),
                        isBought = binding.checkBoxIsBought.isChecked
                    )
                )
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}