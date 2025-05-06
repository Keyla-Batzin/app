package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.bloom.viewmodel.SignUpViewModel
import com.example.bloom.databinding.ActivitySingUpBinding

class SingUp : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private val model: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar el binding correctamente
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar listeners para actualizar el ViewModel
        binding.textUserName.addTextChangedListener {
            model.actualitzaNomUsuari(it.toString())
        }

        binding.textEmail.addTextChangedListener {
            model.actualitzaEmail(it.toString())
        }

        binding.textPass.addTextChangedListener {
            model.actualitzaContrasenya(it.toString())
        }

        binding.textRepPass.addTextChangedListener {
            model.actualitzaRepetirContrasenya(it.toString())
        }

        binding.btnLogin.setOnClickListener {
            // Validar formulario
            model.validarFormulari()

            if (model.formulariValid.value == true) {
                Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SingUp, Login::class.java)
                startActivity(intent)
            }
        }

        binding.textIrALogin.setOnClickListener {
            val intent = Intent(this@SingUp, Login::class.java)
            startActivity(intent)
        }

        // Configurar observadores para mostrar errores
        model.errorNomUsuari.observe(this) { error ->
            binding.textUserName.error = if (error.isNotEmpty()) error else null
        }

        model.errorEmail.observe(this) { error ->
            binding.textEmail.error = if (error.isNotEmpty()) error else null
        }

        model.errorContrasenya.observe(this) { error ->
            binding.textPass.error = if (error.isNotEmpty()) error else null
        }

        model.errorRepetirContrasenya.observe(this) { error ->
            binding.textRepPass.error = if (error.isNotEmpty()) error else null
        }
    }
}