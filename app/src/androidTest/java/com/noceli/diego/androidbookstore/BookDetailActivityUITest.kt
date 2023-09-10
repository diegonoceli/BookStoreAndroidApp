package com.noceli.diego.androidbookstore


import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.noceli.diego.androidbookapi.Book
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import withDrawable

@RunWith(AndroidJUnit4::class)
class BookDetailActivityUITest {

    private lateinit var book: Book

    @Before
    fun setup() {
        book = Book(
            id = "id",
            title = "Sample Book",
            authors = listOf("Author 1", "Author 2"),
            description = "Sample description",
            imageThumbnail = "https://example.com/thumbnail.jpg",
            buyLink = "https://example.com/buy"
        )

        val intent =
            Intent(ApplicationProvider.getApplicationContext(), BookDetailActivity::class.java)
        intent.putExtra("book", book)

        ActivityScenario.launch<BookDetailActivity>(intent)
    }


    @Test
    fun testFavoriteIconClick() {
        Espresso.onView(ViewMatchers.withId(R.id.favoriteIcon))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.favoriteIcon))
            .perform(ViewActions.click())

        val coloredDrawable = R.drawable.ic_favorite_icon_colored
        Espresso.onView(ViewMatchers.withId(R.id.favoriteIcon))
            .check(ViewAssertions.matches(withDrawable(coloredDrawable)))

        Espresso.onView(ViewMatchers.withId(R.id.favoriteIcon))
            .perform(ViewActions.click())

        val borderDrawable = R.drawable.ic_favorite_icon_border
        Espresso.onView(ViewMatchers.withId(R.id.favoriteIcon))
            .check(ViewAssertions.matches(withDrawable(borderDrawable)))
    }

    @Test
    fun testBuyButtonVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.buyButton))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
