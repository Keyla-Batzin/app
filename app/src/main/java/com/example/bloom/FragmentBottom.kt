package com.example.bloom

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
                    // Reemplazar el fragmento con el FragmentHome
                    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, FragmentHome())  // Sustituir por el fragmento adecuado
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                R.id.search -> {
                    // Reemplazar el fragmento con el FragmentSearch
                    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, FragmentSearch())  // Sustituir por el fragmento adecuado
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                R.id.fav -> {
                    // Reemplazar el fragmento con el FragmentFavorites
                    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, FragmentFavorites())  // Sustituir por el fragmento adecuado
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                R.id.profile -> {
                    // Reemplazar el fragmento con el FragmentProfile
                    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, FragmentProfile())  // Sustituir por el fragmento adecuado
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
            true
        }

        return view
    }
}

