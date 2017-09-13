package com.stepstone.stepper.sample;

import android.support.test.filters.LargeTest;

import com.stepstone.stepper.internal.widget.StepTabStateMatcher;
import com.stepstone.stepper.sample.test.action.SpoonScreenshotAction;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCompleteButtonShown;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCurrentStepIs;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkTabState;
import static com.stepstone.stepper.test.StepperNavigationActions.clickComplete;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static org.hamcrest.Matchers.allOf;

/**
 * Performs tests on a styled tabbed stepper i.e. the one with {@code ms_stepperType="tabs"}.
 *
 * @author Piotr Zawadzki
 */
@LargeTest
public class StyledTabsActivityTest extends AbstractActivityTest<StyledTabsActivity> {

    @Test
    public void shouldStayOnTheFirstStepWhenVerificationFails() {
        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(0);
        checkTabState(0, StepTabStateMatcher.TabState.ACTIVE);
        checkTabState(1, StepTabStateMatcher.TabState.INACTIVE);
        checkTabState(2, StepTabStateMatcher.TabState.INACTIVE);
        SpoonScreenshotAction.perform(getScreenshotTag(1, "Verification failure test"));
    }

    @Test
    public void shouldGoToTheNextStepWhenVerificationSucceeds() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(1);
        checkTabState(0, StepTabStateMatcher.TabState.DONE);
        checkTabState(1, StepTabStateMatcher.TabState.ACTIVE);
        checkTabState(2, StepTabStateMatcher.TabState.INACTIVE);
        SpoonScreenshotAction.perform(getScreenshotTag(2, "Verification success test"));
    }

    @Test
    public void shouldShowCompleteButtonOnTheLastStep() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(2);
        checkCompleteButtonShown();
        checkTabState(0, StepTabStateMatcher.TabState.DONE);
        checkTabState(1, StepTabStateMatcher.TabState.DONE);
        checkTabState(2, StepTabStateMatcher.TabState.ACTIVE);
        SpoonScreenshotAction.perform(getScreenshotTag(3, "Last step test"));
    }

    @Test
    public void shouldCompleteStepperFlow() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickComplete());

        //then
        checkCurrentStepIs(2);
        checkTabState(0, StepTabStateMatcher.TabState.DONE);
        checkTabState(1, StepTabStateMatcher.TabState.DONE);
        checkTabState(2, StepTabStateMatcher.TabState.ACTIVE);
        SpoonScreenshotAction.perform(getScreenshotTag(4, "Completion test"));
    }

}
