package com.maliha.i202606

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    // Launch LoginActivity before each test
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setUp() {
        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()
    }

    @Test
    fun testLoginSuccess() {
        // Perform login with valid credentials
        onView(withId(R.id.email_edittxt)).perform(typeText("example@example.com"))
        onView(withId(R.id.pwd_edittxt)).perform(typeText("password"))
        onView(withId(R.id.login_btn)).perform(click())

        // Check if MainPage is launched after successful login
        onView(withId(R.layout.main_pg)).check(matches(isDisplayed()))
    }

    @Test
    fun testLoginFailure() {
        // Perform login with invalid credentials
        onView(withId(R.id.email_edittxt)).perform(typeText("invalid@example.com"))
        onView(withId(R.id.pwd_edittxt)).perform(typeText("invalidpassword"))
        onView(withId(R.id.login_btn)).perform(click())

        // Check if error message is displayed
        onView(withText("Failed to sign in")).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToSignUp() {
        // Click on the sign up button
        onView(withId(R.id.signup_btn)).perform(click())

        // Check if SignUp activity is launched
        onView(withId(R.layout.sign_up)).check(matches(isDisplayed()))
    }

    @Test
    fun testForgotPasswordNavigation() {
        // Click on the forgot password button
        onView(withId(R.id.forgot_pwd_btn)).perform(click())

        // Check if ForgotPwd activity is launched
        onView(withId(R.layout.forgot_pwd)).check(matches(isDisplayed()))
    }
}
