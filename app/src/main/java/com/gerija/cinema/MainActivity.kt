package com.gerija.cinema

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gerija.cinema.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var oldEmail: SharedPreferences
    lateinit var oldPassword: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        oldEmail = getSharedPreferences("email", MODE_PRIVATE)
        oldPassword = getSharedPreferences("password", MODE_PRIVATE)
    }

    /**
     * Проверяю зашел ли пользователь ранее на ак
     */
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    /**
     * Запускаю функцию регистрации или входа в ак
     */
    override fun onResume() {
        super.onResume()
        startNewActivity()
    }

    /**
     * Запускаю активити для регистрации или для входа
     */
    private fun startNewActivity() {

        binding.btRegister.setOnClickListener {
            signUpFirebase()
        }

        binding.tvLogin.setOnClickListener {
            signInFirebase()
        }

    }

    private fun signUpFirebase() {

        val email = binding.inEmail.text.toString()
        val password = binding.inPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        oldEmail.edit().putString("email", email).apply()
                        oldPassword.edit().putString("password", password).apply()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this, "Пользователь уже зарегистрирован",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun signInFirebase() {
        val email = binding.inEmail.text.toString()
        val password = binding.inPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
        } else {
            if(oldEmail.getString("email", "") == email && oldPassword.getString("password", "") == password){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Не найден или не верный пароль", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }
}