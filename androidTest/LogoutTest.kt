package com.maliha.i202606
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LogoutTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MyProfile::class.java)

    @Before
    fun setUp() {
        // Launch MainActivity before each test
        ActivityScenario.launch(MyProfile::class.java)
    }

    @Test
    fun testLogout() {
        // Open the overflow menu
//        onView(withId(R.id.overflow_menu_button)).perform(click())

        // Click on the logout option in the popup menu
        onView(withText(R.id.logout)).perform(click())

        // Check if LoginActivity is launched after logout
        onView(withId(R.layout.login_pg)).check(matches(isDisplayed()))
    }
}
