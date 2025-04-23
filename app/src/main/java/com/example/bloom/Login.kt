package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bloom.pantallahome.ActivityPrincipal
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        splash.setKeepOnScreenCondition { false }

        // Inicializa Firestore
        db = FirebaseFirestore.getInstance()

        // Botón de login
        findViewById<Button>(R.id.btn_login).setOnClickListener {
            // Incrementar el contador de inicios de sesión en Firestore
            incrementLoginCount()
            startActivity(Intent(this, ActivityPrincipal::class.java))
        }

        // Botón de registro
        findViewById<TextView>(R.id.textRegistrar).setOnClickListener {
            startActivity(Intent(this, SingUp::class.java))
        }
    }

    private fun incrementLoginCount() {
        val loginCountRef = db.collection("stats").document("loginCount")

        // Obtener el contador actual de inicios de sesión desde Firestore
        loginCountRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val currentCount = document.getLong("numLogin")?.toInt() ?: 0
                    val newCount = currentCount + 1

                    // Actualizar el contador de inicios de sesión en Firestore
                    loginCountRef.update("numLogin", newCount)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Login count updated successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error updating login count", e)
                        }
                } else {
                    // Si el documento no existe, crearlo con el valor inicial
                    loginCountRef.set(mapOf("numLogin" to 1))
                        .addOnSuccessListener {
                            Log.d("Firestore", "Login count created with value 1")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error creating login count", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting login count", e)
            }
    }
}
