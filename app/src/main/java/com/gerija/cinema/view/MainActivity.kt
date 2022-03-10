package com.gerija.cinema.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.gerija.cinema.R
import com.gerija.cinema.model.firebase.User
import com.gerija.cinema.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
        onSignInResult(it)
    }

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        database = Firebase.database.reference

        startAuthFirebase()
    }


    /**
     * Настройка через что будет аутификация и запуск
     */
    private fun startAuthFirebase() {
        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_Cinema)
            .build()
        signInLauncher.launch(signInIntent)
    }


    /**
     * Получения результата как завершится регистрация ак
     * Запускаю активити для фильмов
     */
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val authUser = FirebaseAuth.getInstance().currentUser

            authUser?.let {
                val firebaseUser = User(it.email.toString(), it.uid)
                database.child("users").child(it.uid).setValue(firebaseUser)

                val intent = Intent(this, MoviesActivity::class.java)
                startActivity(intent)
            }
        } else {
            Log.d("onSignInResultError", "${response?.error?.errorCode}")
        }
    }

}
