
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import my.bcit.rentright.R
import my.bcit.rentright.Views.Activity.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestHomePage {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(HomePageActivity::class.java)

    @Test
    fun testHomeNavigation() {

        onView(withId(R.id.nav_home)).perform(click())
        onView(withId(R.id.container)).check(matches(isDisplayed()))
    }



    @Test
    fun testFavNavigation() {

        onView(withId(R.id.nav_fav)).perform(click())

    }

    @Test
    fun testProfileNavigation() {
        onView(withId(R.id.nav_profile)).perform(click())
    }

    @Test
    fun testRefreshListings() {

        onView(withId(R.id.nav_refresh)).perform(click())

    }
}





