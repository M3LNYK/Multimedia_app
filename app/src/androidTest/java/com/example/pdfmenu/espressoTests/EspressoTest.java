package com.example.pdfmenu.espressoTests;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.*;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.pdfmenu.ADGC_Activity;
import com.example.pdfmenu.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
    @Rule
    public ActivityScenarioRule<ADGC_Activity> activityScenarioRule = new ActivityScenarioRule<ADGC_Activity>(ADGC_Activity.class);

    @Test
    public void searchView_isDisplayedOnTop() {
        onView(withId(R.id.menu_search)).check(isTopAlignedWith(withId(R.layout.activity_adgc)));
    }

    @Test
    public void horizontalScrollView_isBelow_Search() {
        onView(withId(R.id.horizontal_chip)).check(isCompletelyBelow(withId(R.id.menu_search)));
    }

    @Test
    public void horizontalScrollView_isDisplayed() {
        onView(withId(R.id.horizontal_chip)).check(matches(isDisplayed()));
    }

    @Test
    public void searchView_isDisplayed() {
        onView(withId(R.id.menu_search)).check(matches(isDisplayed()));
    }

    @Test
    public void fabOpen_isDisplayed() {
        onView(withId(R.id.floating_open)).check(matches(isDisplayed()));
    }

    @Test
    public void fabEdit_isDisplayed() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.floating_edit)).check(matches(isDisplayed()));
    }

    @Test
    public void fabContinue_isDisplayed() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.floating_continue)).check(matches(isDisplayed()));
    }

    @Test
    public void fabDescription_isDisplayed() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.text_view_fab_edit)).check(matches(isDisplayed()));
    }

    @Test
    public void fabDescription_isDisplayedLeft() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.text_view_fab_edit)).check(isCompletelyLeftOf(withId(R.id.floating_edit)));
    }

    @Test
    public void fabContinue_isDisplayedLeft() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.text_view_fab_continue)).check(isCompletelyLeftOf(withId(R.id.floating_continue)));
    }

    @Test
    public void addPopup_isDisplayed() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.floating_edit)).perform(click());
        onView(withId(R.id.dish_group_autocomplete)).check(matches(isDisplayed()));
    }

    @Test
    public void listView_isDisplayed() {
        onView(withId(R.id.menu_items)).check(matches(isDisplayed()));
    }

    @Test
    public void editItem_isDisplayed() {
        onView(withId(R.id.menu_items)).perform(click());
        onView(withId(R.id.dish_edit_group_autocomplete)).check(matches(isDisplayed()));
    }

//    @Test
//    public void item_isNotDisplayed() {
//        onView(withText("TEST DISH")).check(matches(doesNotExist()));
//    }

    @Test
    public void addItem_isChanging() {
        onView(withId(R.id.floating_open)).perform(click());
        onView(withId(R.id.floating_edit)).perform(click());
        onView(withId(R.id.outlinedTextField_dish_name)).perform(typeText("TEST DISH"));
        onView(withText("TEST DISH")).check(matches(isDisplayed()));
    }

}
