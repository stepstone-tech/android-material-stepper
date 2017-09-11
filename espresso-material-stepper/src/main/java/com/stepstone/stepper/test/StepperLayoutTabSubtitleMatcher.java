package com.stepstone.stepper.test;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.internal.widget.StepTab;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Checks the subtitle of a {@link StepTab} at a specified position.
 *
 * @author Piotr Zawadzki
 */
public class StepperLayoutTabSubtitleMatcher extends BoundedMatcher<View, StepperLayout> {

    private static final String TAG = "StepperLayoutTabSubtMa";

    @IntRange(from = 0)
    private final int tabPosition;

    @NonNull
    private final Matcher<View> subtitleMatcher;

    private StepperLayoutTabSubtitleMatcher(@IntRange(from = 0) int tabPosition, @NonNull Matcher<View> subtitleMatcher) {
        super(StepperLayout.class);
        this.tabPosition = tabPosition;
        this.subtitleMatcher = subtitleMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(" at position: ")
                .appendValue(tabPosition)
                .appendText(" with message: ")
                .appendValue(subtitleMatcher);
    }

    @Override
    protected boolean matchesSafely(StepperLayout view) {
        ViewGroup tabsContainer = (ViewGroup) view.findViewById(com.stepstone.stepper.R.id.ms_stepTabsInnerContainer);
        int childCount = tabsContainer.getChildCount();

        if (childCount == 0) {
            Log.e(TAG, "No tabs found!");
            return false;
        }

        if (tabPosition < 0 || tabPosition >= childCount) {
            Log.e(TAG, "Invalid tab position: " + tabPosition);
            return false;
        }

        StepTab stepTab = (StepTab) tabsContainer.getChildAt(tabPosition);
        TextView subtitleTextView = (TextView) stepTab.findViewById(com.stepstone.stepper.R.id.ms_stepSubtitle);

        return subtitleMatcher.matches(subtitleTextView);
    }

    public static Matcher<View> tabAtPositionHasSubtitle(@IntRange(from = 0) int tabPosition, @NonNull Matcher<View> subtitleMatcher) {
        return new StepperLayoutTabSubtitleMatcher(tabPosition, subtitleMatcher);
    }

}
