package com.example.bloom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

class Fragment_Header : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_header, container, false)

        // Inicializar vistas
        drawerLayout = view.findViewById(R.id.main_drawerlayout)
        navigationView = view.findViewById(R.id.navigation_view)
        toolbar = view.findViewById(R.id.main_toolbar)

        setupNavigationDrawer()

        return view
    }

    private fun setupNavigationDrawer() {
        // Configurar el botón del Drawer en la Toolbar
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar la navegación con NavController
        val navController = findNavController()
        NavigationUI.setupWithNavController(navigationView, navController)
    }
}
