package my.bcit.rentright.views.activity

import android.app.Activity
import android.app.Instrumentation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import my.bcit.rentright.R
import my.bcit.rentright.views.Activity.HomePageActivity
import my.bcit.rentright.views.Activity.Login
import my.bcit.rentright.views.Activity.Signup
import my.bcit.rentright.testUtility.Utils

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestSignup {

    @Before
    fun setUp() {
        Intents.init()
    }
    @Before
    fun launchActivity() {
        ActivityScenario.launch(Signup::class.java)
    }
    @Test
    fun testValidInput_ProceedsToHomePage() {

      Intents.intending(IntentMatchers.hasComponent(HomePageActivity::class.java.name)).respondWith(
          Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail)).perform(typeText("test@email.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())


       Intents.intended(IntentMatchers.hasComponent(HomePageActivity::class.java.name))
    }

    @Test
    fun testFieldsForEmptyInput() {

        val utils = Utils()

        onView(withId(R.id.registerButton)).perform(click())


        onView(withId(R.id.displayName)).check(matches(utils.hasError()))
        onView(withId(R.id.displayEmail)).check(matches(utils.hasError()))
        onView(withId(R.id.displayPassword)).check(matches(utils.hasError()))
    }

    @Test
    fun testInvalidEmail() {
        val utils = Utils()
        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail)).perform(typeText("testemail.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.displayEmail)).check(matches(utils.hasError()))
    }

    @Test
    fun testUnMatchPassword() {
        val utils = Utils()
        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail)).perform(typeText("test@email.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password156"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.displayConfirmPassword)).check(matches(utils.hasError()))

    }

    @Test
    fun testNameForEmptyInput() {
        val utils = Utils()
        onView(withId(R.id.inputEmail)).perform(typeText("test@email.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.displayName)).check(matches(utils.hasError()))

    }

    @Test
    fun testEmailForEmptyInput() {
        val utils = Utils()
        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.displayEmail)).check(matches(utils.hasError()))
    }

    @Test
    fun testPasswordForEmptyInput() {
        val utils = Utils()
        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail)).perform(typeText("test@email.com"), closeSoftKeyboard())
        onView(withId(R.id.inputConfirmPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.displayPassword)).check(matches(utils.hasError()))
    }

    @Test
    fun testConfirmPasswordForEmptyInput() {
        val utils = Utils()
        onView(withId(R.id.inputName)).perform(typeText("Test User"), closeSoftKeyboard())
        onView(withId(R.id.inputEmail)).perform(typeText("test@email.com"), closeSoftKeyboard())
        onView(withId(R.id.inputPassword)).perform(typeText("password123"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.displayConfirmPassword)).check(matches(utils.hasError()))

    }

    @Test
    fun testTitleTextDisplayed() {
        onView(withId(R.id.signUpTXT))
            .check(matches(isDisplayed()))
            .check(matches(withText("Find Your Perfect Home")))
    }

    @Test
    fun testGoToLogin() {
        Intents.intending(IntentMatchers.hasComponent(Login::class.java.name)).respondWith(
            Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        )
        onView(withId(R.id.loginTxt)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(Login::class.java.name))
    }



    @After
    fun cleanUp() {
        Intents.release()
    }
}