package com.stepstone.stepper.sample.test.matcher;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.internal.widget.StepTab;
import com.stepstone.stepper.internal.widget.StepTabStateMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;

/**
 * Checks the state of a {@link StepTab} at a specified position.
 *
 * @author Piotr Zawadzki
 */
public class StepperLayoutTabStateMatcher extends BoundedMatcher<View, StepperLayout> {

    private static final String TAG = "StepperLayoutTabStateMa";

    @IntRange(from = 0)
    private final int tabPosition;

    @NonNull
    private final StepTabStateMatcher.TabState state;

    private StepperLayoutTabStateMatcher(@IntRange(from = 0) int tabPosition, @NonNull StepTabStateMatcher.TabState state) {
        super(StepperLayout.class);
        this.tabPosition = tabPosition;
        this.state = state;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText(" at position: ")
                .appendValue(tabPosition)
                .appendText(" in state: ")
                .appendValue(state.name());
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

        return new StepTabStateMatcher(state).matches(stepTab);
    }

    public static Matcher<View> tabAtPositionIsInState(@IntRange(from = 0) int tabPosition, @NonNull StepTabStateMatcher.TabState state) {
        return new StepperLayoutTabStateMatcher(tabPosition, checkNotNull(state));
    }

}
