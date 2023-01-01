package com.michael.tubesmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.michael.tubesmobile.databinding.ActivityMainBinding
import com.michael.tubesmobile.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}