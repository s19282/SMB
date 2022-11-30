package com.example.smb_p01

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smb_p01.databinding.ActivityProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pvm = ProductViewModel(application)
        val adapter = ProductAdapter(pvm)
        binding.addButton.text = intent.getStringExtra("mode")
        binding.topText.text = intent.getStringExtra("mode") + " product"
        binding.editTextTextProductName.setText(intent.getStringExtra("name"))
        binding.editTextTextProductAmount.setText(intent.getStringExtra("amount"))
        binding.editTextTextProductPrice.setText(intent.getStringExtra("price"))
        binding.checkBoxIsBought.isChecked = intent.getBooleanExtra("isBought", false)

        binding.rejectAndCloseButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.addButton.setOnClickListener {
            CoroutineScope(IO).launch {
                if (intent.getStringExtra("mode").equals("Add")) {
                    adapter.add(
                        Product(
                            name = binding.editTextTextProductName.text.toString(),
                            price = binding.editTextTextProductPrice.text.toString().toDouble(),
                            amount = binding.editTextTextProductAmount.text.toString().toDouble(),
                            isBought = binding.checkBoxIsBought.isChecked
                        )
                    ).let { id ->
                        val intent = Intent(applicationContext, ProductActivity::class.java)
                        intent.setClassName(
                            "com.example.broadcastreceiverapp",
                            "com.example.broadcastreceiverapp.ProductReceiver"
                        )
                        intent.putExtra("id", id)
                        intent.putExtra("name", binding.editTextTextProductName.text.toString())
                        intent.putExtra(
                            "price",
                            binding.editTextTextProductPrice.text.toString()
                        )
                        intent.putExtra(
                            "amount",
                            binding.editTextTextProductAmount.text.toString()
                        )
                        intent.putExtra("isBought", binding.checkBoxIsBought.isChecked)
                        intent.action = "com.example.smb_p01.action.PRODUCT_ADDED"
                        //sendBroadcast(intent)
                        sendBroadcast(intent,"com.example.smb_p01.permission.ProductNotification")
//                        sendBroadcast(intent,Manifest.permission.ProductNotification)
                    }
                } else {
                    println("id: "+intent.getLongExtra("id",0))
                    adapter.update(
                        Product(
                            id = intent.getLongExtra("id", 0),
                            name = binding.editTextTextProductName.text.toString(),
                            price = binding.editTextTextProductPrice.text.toString().toDouble(),
                            amount = binding.editTextTextProductAmount.text.toString().toDouble(),
                            isBought = binding.checkBoxIsBought.isChecked
                        )
                    )
                }
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.coordinatorLayout3.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "backgroundColor",
                    "#50F1A0"
                )
            )
        )
        binding.topText.textSize = sp.getString("fontSize", "20")!!.toFloat()

    }
}