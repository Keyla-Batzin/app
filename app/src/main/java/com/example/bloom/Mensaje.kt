package com.example.bloom

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object Mensaje {

    // Método para mostrar mensaje de "Añadido a favoritos"
    fun mostrarFavoritos(view: View?, @ColorRes colorRes: Int = R.color.lila_azul) {
        mostrarSnackbarPersonalizado(
            view = view,
            mensaje = "💜 Añadido a favoritos",
            colorRes = colorRes
        )
    }

    // Método para mostrar mensaje de "Añadido a la cesta"
    fun mostrarCesta(view: View?, accionDeshacer: (() -> Unit)? = null, @ColorRes colorRes: Int = R.color.lila_claro) {
        mostrarSnackbarPersonalizado(
            view = view,
            mensaje = "✔️ Añadido a la cesta",
            colorRes = colorRes,
            accion = accionDeshacer
        )
    }

    fun mostrarPersonalizado(
        view: View?,
        mensaje: String,
        @ColorRes colorRes: Int = R.color.morado_intermedio,
        textoAccion: String? = null,
        accion: (() -> Unit)? = null,
        duracion: Int = Snackbar.LENGTH_LONG
    ) {
        view?.let { rootView ->
            val snackbar = Snackbar.make(rootView, mensaje, duracion).apply {
                setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                setBackgroundTint(ContextCompat.getColor(rootView.context, colorRes))

                textoAccion?.let { text ->
                    accion?.let { action ->
                        setAction(text) { action.invoke() }
                    }
                }
            }

            // Centrar texto
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }

            snackbar.show()
        }
    }


    // Método base reusable para Snackbars
    private fun mostrarSnackbarPersonalizado(
        view: View?,
        mensaje: String,
        @ColorRes colorRes: Int,
        textoAccion: String? = null,
        accion: (() -> Unit)? = null
    ) {
        view?.let { rootView ->
            val snackbar = Snackbar.make(rootView, mensaje, Snackbar.LENGTH_LONG).apply {
                setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                rootView.context?.let { context ->
                    setBackgroundTint(ContextCompat.getColor(context, colorRes))
                }

                // Configurar acción si existe
                textoAccion?.let { text ->
                    accion?.let { action ->
                        setAction(text) { action.invoke() }
                    }
                }
            }

            // Centrar texto
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }

            snackbar.show()
        }
    }
}