package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bloom.pantallahome.ActivityPrincipal

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // SplashScreen
        val splash = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        splash.setKeepOnScreenCondition{ false }

        // Botón de login
        findViewById<Button>(R.id.btn_login).setOnClickListener {
            startActivity(Intent(this, ActivityPrincipal::class.java))
        }

        // Botón de registro
        findViewById<TextView>(R.id.textRegistrar).setOnClickListener {
            startActivity(Intent(this, SingUp::class.java))
        }
    }
}

