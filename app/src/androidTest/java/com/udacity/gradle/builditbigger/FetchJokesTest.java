package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(JUnit4.class)
@LargeTest
public class FetchJokesTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule mMainActivity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = ((MainActivity) mMainActivity.getActivity()).registerIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void tearDown() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void fetchJokesFromGceTest() {
        // Click button to fetch jokes from GCE
        onView(withId(R.id.btn_tell_joke))
                .check(matches(isDisplayed()))
                .perform(click());

        // Check that the joke title is not empty
        onView(withId(R.id.text_joke_category))
                .check(matches(isDisplayed()))
                .check(matches(not(withText(""))));

        // Check that joke description id not empty
        onView(withId(R.id.text_joke))
                .check(matches(isDisplayed()))
                .check(matches(not(withText(""))));
    }
}
