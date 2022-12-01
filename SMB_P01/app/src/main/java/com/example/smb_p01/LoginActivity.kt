package com.example.smb_p01

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smb_p01.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser == null) {
            binding.registerButton.setOnClickListener {
                val login = binding.editTextTextEmailAddress.text.toString()
                val password = binding.editTextTextPassword.text.toString()
                auth.createUserWithEmailAndPassword(
                    login,
                    password
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(this, "Registered!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            binding.loginButton.setOnClickListener {
                val login = binding.editTextTextEmailAddress.text.toString()
                val password = binding.editTextTextPassword.text.toString()
                auth.signInWithEmailAndPassword(
                    login,
                    password
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Logged id!", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            val user = auth.currentUser
                        } else {
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}