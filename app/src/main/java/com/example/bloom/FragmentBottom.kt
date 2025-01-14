package com.example.bloom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentBottom : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_bottom, container, false)

        // Inicializar el BottomNavigationView
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        // Configurar la navegación con el BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // Reemplazar el fragmento con la Activity
                    val intent = Intent(activity, Home::class.java)
                    startActivity(intent)
                    true;
                }
                R.id.search -> {
                    // Reemplazar el fragmento con la Activity
                    val intent = Intent(activity, Search::class.java)
                    startActivity(intent)
                    true;
                }
                R.id.fav -> {
                    // Reemplazar el fragmento con la Activity
                    val intent = Intent(activity, Favorites::class.java)
                    startActivity(intent)
                    true;
                }
                R.id.profile -> {
                    // Reemplazar el fragmento con la Activity
                    val intent = Intent(activity, Profile::class.java)
                    startActivity(intent)
                    true;
                }
            }
            true
        }

        return view
    }
}

