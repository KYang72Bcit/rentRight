package my.bcit.rentright.Views.Activity

import android.app.Activity
import android.app.Instrumentation
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
class SignupTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Signup::class.java)


    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(Signup::class.java)
    }




    @Test
    fun checkTitleTextDisplayed() {
        onView(withId(R.id.title))
            .check(matches(isDisplayed()))
            .check(matches(withText("Find Your Perfect Home")))
    }

    @Test
    fun checkSubtitleTextDisplayed() {
        onView(withId(R.id.loginTxt))
            .check(matches(isDisplayed()))
            .check(matches(withText("Already have an account? Login")))
    }

    @Test
    fun checkImageViewDisplayed() {
        onView(withId(R.id.logo))
            .check(matches(isDisplayed()))
    }

    fun testValidInput_ProceedsToHomePage() {

//        Intents.intending(IntentMatchers.hasComponent(HomePageActivity::class.java.name)).respondWith(
//            Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail)).perform(typeText("test@email.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password123"), closeSoftKeyboard())

        // Click the register button
        onView(withId(R.id.registerButton)).perform(click())

        // Check if the app navigates to the HomePageActivity
        Intents.intended(IntentMatchers.hasComponent(HomePageActivity::class.java.name))
    }

//    @Test
//    fun checkGetStartedButtonClickNavigatesToSignup() {
//        Intents.intending(IntentMatchers.hasComponent(Signup::class.java.name)).respondWith(
//            Instrumentation.ActivityResult(Activity.RESULT_OK, null))
//
//        onView(withId(R.id.getStartedButton)).perform(click())
//
//        Intents.intended(IntentMatchers.hasComponent(Signup::class.java.name))
//
//    }




    @After
    fun cleanUp() {
        Intents.release()
    }
}