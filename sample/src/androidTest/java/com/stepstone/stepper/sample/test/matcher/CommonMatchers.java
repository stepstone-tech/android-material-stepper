package com.stepstone.stepper.sample.test.matcher;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import com.stepstone.stepper.internal.widget.StepTabStateMatcher;
import com.stepstone.stepper.sample.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.stepstone.stepper.sample.test.matcher.StepperLayoutTabStateMatcher.tabAtPositionIsInState;
import static com.stepstone.stepper.sample.test.matcher.ViewPagerPositionMatcher.hasPagePosition;
import static com.stepstone.stepper.test.StepperLayoutTabSubtitleMatcher.tabAtPositionHasSubtitle;

/**
 * Contains commonly used matchers.
 *
 * @author Piotr Zawadzki
 */
public final class CommonMatchers {

    private CommonMatchers() {
    }

    public static void checkTabState(@IntRange(from = 0) int position, @NonNull StepTabStateMatcher.TabState state) {
        onView(withId(R.id.stepperLayout)).check(matches(tabAtPositionIsInState(position, state)));
    }

    public static void checkTabSubtitle(@IntRange(from = 0) int position, @NonNull Matcher<View> subtitleMatcher) {
        onView(withId(R.id.stepperLayout)).check(matches(tabAtPositionHasSubtitle(position, subtitleMatcher)));
    }

    public static void checkCurrentStepIs(@IntRange(from = 0) int expectedCurrentStep) {
        onView(withId(R.id.ms_stepPager)).check(matches(hasPagePosition(expectedCurrentStep)));
    }

    public static void checkCompleteButtonShown() {
        onView(withId(R.id.ms_stepCompleteButton)).check(matches(isDisplayed()));
    }

}
