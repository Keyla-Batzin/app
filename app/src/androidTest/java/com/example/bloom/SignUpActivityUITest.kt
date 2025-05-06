package com.example.bloom

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpActivityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SingUp::class.java)

    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.textUserName)).perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textUserName))
            .check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }

    @Test
    fun testNomUsuariComencaSimbol() {
        onView(withId(R.id.textUserName)).perform(clearText(), typeText("&usuari"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textUserName))
            .check(matches(hasErrorText("El nom d'usuari no pot començar per símbol")))
    }

    @Test
    fun testEmailBuit() {
        onView(withId(R.id.textEmail)).perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textEmail))
            .check(matches(hasErrorText("L'email és obligatori")))
    }

    @Test
    fun testEmailInvalid() {
        onView(withId(R.id.textEmail)).perform(clearText(), typeText("usuari sense arroba"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textEmail))
            .check(matches(hasErrorText("L'email no és vàlid")))
    }

    @Test
    fun testContrasenyaCurta() {
        onView(withId(R.id.textPass)).perform(clearText(), typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textPass))
            .check(matches(hasErrorText("La contrasenya ha de tenir mínim 6 caràcters")))
    }

    @Test
    fun testContrasenyesNoCoincideixen() {
        onView(withId(R.id.textPass)).perform(clearText(), typeText("contrasenya1"), closeSoftKeyboard())
        onView(withId(R.id.textRepPass)).perform(clearText(), typeText("contrasenya2"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.textRepPass))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }

    @Test
    fun testFormulariValid() {
        onView(withId(R.id.textUserName)).perform(clearText(), typeText("usuariTest"), closeSoftKeyboard())
        onView(withId(R.id.textEmail)).perform(clearText(), typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.textPass)).perform(clearText(), typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.textRepPass)).perform(clearText(), typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.btn_login))
            .check(matches(isDisplayed()))
    }
}