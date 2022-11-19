package com.example.broadcastreceiverapp

import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var receiver: ProductReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityResultContracts.RequestPermission()
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(
                    applicationContext,
                    "Permissions have not been granted, notifications will not work",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        println("receiver created")
        receiver = ProductReceiver()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(
            receiver,
            IntentFilter("com.example.smb_p01.action.AddProduct")
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}