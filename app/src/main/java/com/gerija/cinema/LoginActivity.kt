package com.gerija.cinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btExit = findViewById<Button>(R.id.btExit)
        auth = Firebase.auth
        btExit.setOnClickListener {
            auth.signOut()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        auth.signOut()
    }
}