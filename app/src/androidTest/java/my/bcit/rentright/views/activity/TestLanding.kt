package my.bcit.rentright.Views.Activity

import android.app.Activity
import android.app.Instrumentation
import android.util.Log
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
import my.bcit.rentright.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LandingTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Landing::class.java)


    @Before
    fun setUp() {
        Intents.init()
    }
    @Before
    fun launchActivity() {
        ActivityScenario.launch(Landing::class.java)
    }


    @Test
    fun testTitleTextDisplayed() {

        val startTime = System.currentTimeMillis()

        onView(withId(R.id.titleText))
            .check(matches(isDisplayed()))
            .check(matches(withText("Find Your Perfect Stay")))

        val endTime = System.currentTimeMillis()
        Log.d("TestLatency", "Time taken to display title text: ${endTime - startTime} ms")
    }

    @Test
    fun testSubtitleTextDisplayed() {

        val startTime = System.currentTimeMillis()

        onView(withId(R.id.subtitleText))
            .check(matches(isDisplayed()))
            .check(matches(withText("Discover your dream place to live")))

        val endTime = System.currentTimeMillis()
        Log.d("TestLatency", "Time taken to display subtitle text: ${endTime - startTime} ms")
    }

    @Test
    fun testImageViewDisplayed() {

        val startTime = System.currentTimeMillis()

        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))

        val endTime = System.currentTimeMillis()
        Log.d("TestLatency", "Time taken to display image view: ${endTime - startTime} ms")
    }

    @After
    fun cleanUp() {
        Intents.release()
    }
}