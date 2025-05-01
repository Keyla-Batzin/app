package com.example.bloom

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpActivityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SingUp::class.java)

    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.textUserName)).perform(clearText())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textUserName))
            .check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }

    @Test
    fun testNomUsuariComençaSimbol() {
        onView(withId(R.id.textUserName)).perform(clearText(), typeText("&usuari"))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textUserName))
            .check(matches(hasErrorText("El nom d'usuari no pot començar per símbol")))
    }

    @Test
    fun testEmailBuit() {
        onView(withId(R.id.textEmail)).perform(clearText())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textEmail))
            .check(matches(hasErrorText("L'email és obligatori")))
    }

    @Test
    fun testEmailInvalid() {
        onView(withId(R.id.textEmail)).perform(clearText(), typeText("usuari sense arroba"))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textEmail))
            .check(matches(hasErrorText("L'email no és vàlid")))
    }

    @Test
    fun testContrasenyaCurta() {
        onView(withId(R.id.textPass)).perform(clearText(), typeText("123"))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textPass))
            .check(matches(hasErrorText("La contrasenya ha de tenir mínim 6 caràcters")))
    }

    @Test
    fun testContrasenyesNoCoincideixen() {
        onView(withId(R.id.textPass)).perform(clearText(), typeText("contrasenya1"))
        onView(withId(R.id.textRepPass)).perform(clearText(), typeText("contrasenya2"))
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textRepPass))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }

    @Test
    fun testFormulariValid() {
        onView(withId(R.id.textUserName)).perform(clearText(), typeText("usuariTest"))
        onView(withId(R.id.textEmail)).perform(clearText(), typeText("test@example.com"))
        onView(withId(R.id.textPass)).perform(clearText(), typeText("password123"))
        onView(withId(R.id.textRepPass)).perform(clearText(), typeText("password123"))
        onView(withId(R.id.btn_login)).perform(click())

        // Aquí podrías añadir una comprobación adicional si la navegación a la siguiente pantalla es instantánea
        // o si hay algún feedback visual en la misma pantalla (ej. un Toast no se puede verificar fácilmente con Espresso).
        // Por ahora, solo verificamos que no hay errores visibles.
        onView(withId(R.id.textUserName)).check(matches(not(hasErrorText("El nom d'usuari és obligatori"))))
        onView(withId(R.id.textEmail)).check(matches(not(hasErrorText("L'email és obligatori"))))
        onView(withId(R.id.textEmail)).check(matches(not(hasErrorText("L'email no és vàlid"))))
        onView(withId(R.id.textPass)).check(matches(not(hasErrorText("La contrasenya ha de tenir mínim 6 caràcters"))))
        onView(withId(R.id.textRepPass)).check(matches(not(hasErrorText("Les contrasenyes no coincideixen"))))
    }
}