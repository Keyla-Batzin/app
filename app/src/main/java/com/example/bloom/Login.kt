package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener { // Cambiar a la MainActivity
            val intent: Intent =
                Intent(
                    this@Login,
                    MainActivity::class.java
                )
            startActivity(intent)
        }

        val textRegistrar = findViewById<TextView>(R.id.textRegistrar)

        textRegistrar.setOnClickListener {
            // Cambiar a la actividad SingUp
            val intent = Intent(this@Login, SingUp::class.java)
            startActivity(intent)
        }
    }
}