package com.example.bloom

import com.example.bloom.viewmodel.SignUpViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

class SignUpViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = SignUpViewModel()

    @Test
    fun `comprovaNomUsuari retorna error quan el nom d'usuari està buit`() {
        viewModel.actualitzaNomUsuari("")
        viewModel.comprovaNomUsuari()
        assertEquals("El nom d'usuari és obligatori", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `comprovaNomUsuari retorna error quan el nom d'usuari comença per símbol`() {
        viewModel.actualitzaNomUsuari("&usuari")
        viewModel.comprovaNomUsuari()
        assertEquals("El nom d'usuari no pot començar per símbol", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `comprovaNomUsuari no retorna error amb un nom d'usuari vàlid`() {
        viewModel.actualitzaNomUsuari("usuari123")
        viewModel.comprovaNomUsuari()
        assertEquals("", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `comprovaEmail retorna error quan l'email està buit`() {
        viewModel.actualitzaEmail("")
        viewModel.comprovaEmail()
        assertEquals("L'email és obligatori", viewModel.errorEmail.value)
    }

    @Test
    fun `comprovaEmail retorna error quan l'email no és vàlid`() {
        viewModel.actualitzaEmail("usuari sense arroba")
        viewModel.comprovaEmail()
        assertEquals("L'email no és vàlid", viewModel.errorEmail.value)
    }

    @Test
    fun `comprovaEmail no retorna error amb un email vàlid`() {
        viewModel.actualitzaEmail("test@example.com")
        viewModel.comprovaEmail()
        assertEquals("", viewModel.errorEmail.value)
    }

    @Test
    fun `comprovaContrasenya retorna error quan la contrasenya és massa curta`() {
        viewModel.actualitzaContrasenya("123")
        viewModel.comprovaContrasenya()
        assertEquals("La contrasenya ha de tenir mínim 6 caràcters", viewModel.errorContrasenya.value)
    }

    @Test
    fun `comprovaContrasenya no retorna error amb una contrasenya vàlida`() {
        viewModel.actualitzaContrasenya("password123")
        viewModel.comprovaContrasenya()
        assertEquals("", viewModel.errorContrasenya.value)
    }

    @Test
    fun `comprovaRepetirContrasenya retorna error quan no coincideixen`() {
        viewModel.actualitzaContrasenya("password")
        viewModel.actualitzaRepetirContrasenya("different")
        viewModel.comprovaRepetirContrasenya()
        assertEquals("Les contrasenyes no coincideixen", viewModel.errorRepetirContrasenya.value)
    }

    @Test
    fun `comprovaRepetirContrasenya no retorna error quan coincideixen`() {
        viewModel.actualitzaContrasenya("password")
        viewModel.actualitzaRepetirContrasenya("password")
        viewModel.comprovaRepetirContrasenya()
        assertEquals("", viewModel.errorRepetirContrasenya.value)
    }

    @Test
    fun `validarFormulari retorna true amb dades vàlides`() {
        viewModel.actualitzaNomUsuari("usuariTest")
        viewModel.actualitzaEmail("test@example.com")
        viewModel.actualitzaContrasenya("password123")
        viewModel.actualitzaRepetirContrasenya("password123")
        viewModel.validarFormulari()
        assertTrue(viewModel.formulariValid.value!!)
    }

    @Test
    fun `validarFormulari retorna false amb alguna dada invàlida`() {
        viewModel.actualitzaNomUsuari("")
        viewModel.actualitzaEmail("test@example.com")
        viewModel.actualitzaContrasenya("password123")
        viewModel.actualitzaRepetirContrasenya("password123")
        viewModel.validarFormulari()
        assertFalse(viewModel.formulariValid.value!!)
    }
}