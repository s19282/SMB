package com.example.broadcastreceiverapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var receiver: ProductReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (!isGranted) {
//                Toast.makeText(
//                    applicationContext,
//                    "Permissions have not been granted, notifications will not work",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//        receiver = ProductReceiver()
//        val filter = IntentFilter("com.example.smb_p01.action.PRODUCT_ADDED")
//
//        registerReceiver(
//            receiver,
//            IntentFilter()
//        )
    }

    override fun onStart() {
        super.onStart()
//        println("register")
//        registerReceiver(
//            receiver,
//            IntentFilter("com.example.smb_p01.action.PRODUCT_ADDED")
//        )
//        println("registered")
    }

    override fun onStop() {
        super.onStop()
        //unregisterReceiver(receiver)
    }
}