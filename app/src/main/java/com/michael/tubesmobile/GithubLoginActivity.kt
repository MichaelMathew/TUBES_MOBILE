package com.michael.tubesmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.michael.tubesmobile.databinding.ActivityGithubLoginBinding

class GithubLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGithubLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.trim().isNotEmpty()) {
                val provider = OAuthProvider.newBuilder("github.com")
                provider.addCustomParameter("login", binding.etEmail.text.toString());
                val scopes: ArrayList<String?> = object : ArrayList<String?>() {
                    init {
                        add("user:email")
                    }
                }
                provider.scopes = scopes
                val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult
                if (pendingResultTask != null) {

                    pendingResultTask
                        .addOnSuccessListener(
                            OnSuccessListener {
                                Toast.makeText(this@GithubLoginActivity, it.toString(), Toast.LENGTH_SHORT).show()
                                Log.d("WASD", it.toString())
                                updateUI()
                            })
                        .addOnFailureListener(
                            OnFailureListener {
                                Toast.makeText(this@GithubLoginActivity, it.toString(), Toast.LENGTH_SHORT).show()
                                Log.d("WASD", it.toString())
                            })
                } else {
                    firebaseAuth
                        .startActivityForSignInWithProvider( this@GithubLoginActivity, provider.build())
                        .addOnSuccessListener {
                            updateUI()
                            Log.d("WASD", it.toString())
                        }
                        .addOnFailureListener {
                            Log.d("WASD", it.toString())
                            Toast.makeText(this@GithubLoginActivity, it.toString(), Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Empty Email", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateUI() {
        startActivity(
            Intent(this@GithubLoginActivity, SecondActivity::class.java)
        )
        finish()
    }
}