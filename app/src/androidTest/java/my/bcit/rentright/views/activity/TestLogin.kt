package my.bcit.rentright.views.activity

import androidx.test.core.app.ActivityScenario

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import my.bcit.rentright.R
import my.bcit.rentright.views.Activity.Login
import my.bcit.rentright.testUtility.Utils
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
        onView(withId(R.id.loginButton)).perform(click())
//        onView(withId(R.id.displayEmail)).check(matches(utils.hasError()))


    }

    @After
    fun cleanUp() {
        Intents.release()
    }






}