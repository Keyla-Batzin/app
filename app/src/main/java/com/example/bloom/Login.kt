package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bloom.databinding.ActivityMainBinding
import com.example.bloom.pantallahome.ActivityPrincipal
import com.example.bloom.pantallahome.MainActivity

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Configurar SplashScreen
        val splashScreen = installSplashScreen()

        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { false }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener { // Cambiar a la MainActivity
            val intent: Intent =
                Intent(this@Login, PruebaAPi::class.java)
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