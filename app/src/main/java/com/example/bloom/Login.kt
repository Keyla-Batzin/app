package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bloom.pantallahome.ActivityPrincipal
import com.example.bloom.stats.Stats
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    private val PREFS_FILENAME = "com.example.bloom.prefs"
    private val LOGIN_COUNT = "login_count"
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
            // Incrementar el contador de inicios de sesión
            incrementLoginCount()
            startActivity(Intent(this, ActivityPrincipal::class.java))
        }

        // Botón de registro
        findViewById<TextView>(R.id.textRegistrar).setOnClickListener {
            startActivity(Intent(this, SingUp::class.java))
        }
    }

    private fun incrementLoginCount() {
        val prefs = getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)
        val currentCount = prefs.getInt(LOGIN_COUNT, 0)
        val newCount = currentCount + 1

        // Guardar el nuevo contador localmente
        val editor = prefs.edit()
        editor.putInt(LOGIN_COUNT, newCount)
        editor.apply()

        // Actualizar el contador en Firestore
        val loginCountData = hashMapOf("numLogin" to newCount)
        db.collection("stats").document("loginCount")
            .set(loginCountData)
            .addOnSuccessListener {
                Log.d("Firestore", "Login count updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error updating login count", e)
            }
    }

}
