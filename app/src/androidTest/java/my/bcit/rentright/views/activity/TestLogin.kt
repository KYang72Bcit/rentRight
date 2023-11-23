package my.bcit.rentright.views.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import my.bcit.rentright.R
import my.bcit.rentright.Views.Activity.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Login::class.java)


    @Test
    fun testLoginUIElementsDisplayed() {
        onView(withId(R.id.inputEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.inputPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.btnGoToSignUp)).check(matches(isDisplayed()))
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
        onView(withId(R.id.checkRememberMe)).check(matches(isDisplayed()))
        onView(withId(R.id.displayForgetPassword)).check(matches(isDisplayed()))
    }


    @Test
    fun testLoginSuccess() {
        onView(withId(R.id.inputEmail)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())
    }

    @Test
    fun testRememberMeFunctionality() {
        onView(withId(R.id.checkRememberMe)).perform(click())
        onView(withId(R.id.checkRememberMe)).check(matches(isChecked()))
    }

    @Test
    fun testGoToSignup() {
        onView(withId(R.id.btnGoToSignUp)).perform(click())
        Intents.intended(hasComponent(Signup::class.java.name))
    }

    @After
    fun cleanUp() {
        Intents.release()
    }
}

