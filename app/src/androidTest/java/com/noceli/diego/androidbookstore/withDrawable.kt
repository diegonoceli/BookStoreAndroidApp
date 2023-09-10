import android.view.View
import android.widget.ImageView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withDrawable(resourceId: Int): Matcher<View> {
    return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource ID: $resourceId")
        }

        override fun matchesSafely(imageView: ImageView): Boolean {
            return imageView.context.getDrawable(resourceId)?.constantState ==
                    imageView.drawable.constantState
        }
    }
}
