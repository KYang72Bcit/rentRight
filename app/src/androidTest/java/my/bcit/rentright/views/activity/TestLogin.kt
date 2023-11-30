package my.bcit.rentright.views.activity

import android.util.Log
import my.bcit.rentright.testUtility.Utils
import android.app.Activity
import android.app.Instrumentation
import android.view.View
import androidx.test.core.app.ActivityScenario
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


val startTime = System.currentTimeMillis()


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

    fun testEmailForEmpty(){
        var utils = Utils()
        onView(withId(R.id.inputPassword)).perform(typeText("password123"))

        // Start timing before the click
        val startTime = System.currentTimeMillis()

        onView(withId(R.id.loginButton)).perform(click())
//        onView(withId(R.id.displayEmail)).check(matches(utils.hasError()))

        // Stop timing after the expected result
        val endTime = System.currentTimeMillis()

        // Log the latency
        val latency = endTime - startTime
        Log.d("TestLatency", "Login operation took $latency milliseconds.")

    }

    @After
    fun cleanUp() {
        Intents.release()
    }
  }
}

