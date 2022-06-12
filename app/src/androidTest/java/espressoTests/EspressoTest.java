package espressoTests;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.PositionAssertions.isBelow;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.widget.HorizontalScrollView;

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
    public void horizontalScrollView_isDisplayed(){
        onView(withId(R.id.horizontal_chip)).check(matches(isDisplayed()));
    }

    @Test
    public void horizontalScrollView_isBelow_Search(){
        onView(withId(R.id.horizontal_chip)).check(isBelow(withId(R.id.menu_search)));
    }
}
