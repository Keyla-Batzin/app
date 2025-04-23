package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.bloom.viewmodel.SignUpViewModel
import com.example.bloom.databinding.ActivitySingUpBinding

class SingUp : AppCompatActivity() {

    lateinit var binding: ActivitySingUpBinding
    private val model: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_up)

        binding.textUserName.addTextChangedListener {
            model.actualitzaNomUsuari(it.toString())
        }


        val btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {
            // Mostrar un Toast antes de cambiar de actividad
            Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()

            // Cambiar a la Login al crear la cuenta
            val intent = Intent(this@SingUp, Login::class.java)
            startActivity(intent)
        }

        val textLogin = findViewById<TextView>(R.id.textIrALogin)
        textLogin.setOnClickListener {
            // Cambiar a la actividad Login
            val intent = Intent(this@SingUp, Login::class.java)
            startActivity(intent)
        }

    }
}