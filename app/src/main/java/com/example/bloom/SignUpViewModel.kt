package com.example.bloom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private var _nomUsuari: String = ""
    private var _email: String = ""
    private var _contrasenya: String = ""

    private val _formulariValid = MutableLiveData(false)
    val formulariValid: LiveData<Boolean> = _formulariValid

    private val _errorNomUsuari = MutableLiveData<String>("")
    val errorNomUsuari: LiveData<String> = _errorNomUsuari

    private val _errorEmail = MutableLiveData<String>("")
    val errorEmail: LiveData<String> = _errorEmail

    private val _errorContrasenya = MutableLiveData<String>("")
    val errorContrasenya: LiveData<String> = _errorContrasenya

    fun actualitzaNomUsuari(nom: String) {
        _nomUsuari = nom
    }

    fun actualitzaEmail(email: String) {
        _email = email
    }

    fun actualitzaContrasenya(pwd: String) {
        _contrasenya = pwd
    }

    fun comprovaNomUsuari() {
        if (_nomUsuari.isBlank()) {
            _errorNomUsuari.value = "El nom d'usuari és obligatori"
        } else if (_nomUsuari.firstOrNull()?.isLetterOrDigit() == false) {
            _errorNomUsuari.value = "El nom d'usuari no pot començar per símbol"
        } else {
            _errorNomUsuari.value = ""
        }
    }

    fun comprovaEmail() {
        if (_email.isBlank()) {
            _errorEmail.value = "L'email és obligatori"
        } else if (!_email.contains("@")) {
            _errorEmail.value = "L'email no és vàlid"
        } else {
            _errorEmail.value = ""
        }
    }

    fun comprovaContrasenya() {
        if (_contrasenya.length < 6) {
            _errorContrasenya.value = "La contrasenya ha de tenir mínim 6 caràcters"
        } else {
            _errorContrasenya.value = ""
        }
    }

    fun validarFormulari() {
        comprovaNomUsuari()
        comprovaEmail()
        comprovaContrasenya()

        _formulariValid.value =
            _errorNomUsuari.value.isNullOrBlank() &&
                    _errorEmail.value.isNullOrBlank() &&
                    _errorContrasenya.value.isNullOrBlank()
    }

}
