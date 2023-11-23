package my.bcit.rentright.views.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import my.bcit.rentright.R
import my.bcit.rentright.Views.Activity.ListingDetailActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListingDetailActivityTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(ListingDetailActivity::class.java)


    @Test
    fun testDisplayElements() {
        onView(withId(R.id.rentTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.addressTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.cityTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.stateTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.zipcodeTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.descriptionTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testBackButton() {
        onView(withId(R.id.backButton)).perform(click())
    }

    @Test
    fun testCallButton() {
        onView(withId(R.id.callButton)).perform(click())
    }

    @Test
    fun testEmailButton() {
        onView(withId(R.id.emailButton)).perform(click())
    }

    @After
    fun cleanUp() {
        Intents.release()
    }
}
