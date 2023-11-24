package my.bcit.rentright.views.activity

import android.app.Activity
import android.app.Instrumentation
import android.view.View
import androidx.media3.common.util.Log
import androidx.test.core.app.ActivityScenario

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputLayout
import my.bcit.rentright.R
import my.bcit.rentright.Views.Activity.HomePageActivity
import my.bcit.rentright.Views.Activity.Landing
import my.bcit.rentright.Views.Activity.Login
import my.bcit.rentright.Views.Activity.Signup
import my.bcit.rentright.testUtility.Utils
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

val startTime = System.currentTimeMillis()

@RunWith(AndroidJUnit4::class)
class TestLogin {

    @Before
    fun setUp() {
        Intents.init()
    }
    @Before
    fun launchActivity() {
        ActivityScenario.launch(Login::class.java)
    }

    @Test
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