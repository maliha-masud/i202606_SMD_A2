import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.maliha.i202606.MentorPg
import org.junit.Rule
import com.maliha.i202606.R
import org.junit.Test

class MentorFavouriteTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MentorPg> =
        ActivityScenarioRule(MentorPg::class.java)

    @Test
    fun testFavoriteButton() {
        // Assuming the favorite button has ID favButton
        onView(withId(R.id.fav_1)).perform(click())

        // Check if the button text changes after clicking
        onView(withId(R.id.fav_1)).check(matches(withText("Unfavorite")))

        // Click again to unfavorite
        onView(withId(R.id.fav_1)).perform(click())

        // Check if the button text changes after clicking again
        onView(withId(R.id.fav_1)).check(matches(withText("Favorite")))
    }
}
