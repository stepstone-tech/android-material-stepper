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
import static com.stepstone.stepper.test.StepperNavigationActions.clickBack;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static org.hamcrest.Matchers.allOf;

/**
 * Performs tests on a tabbed stepper i.e. the one with {@code ms_stepperType="tabs"}.
 * This also tests if the errors are shown in the tabs and stay there when going back.
 *
 * @author Piotr Zawadzki
 */
@LargeTest
public class ShowErrorOnBackTabActivityTest extends AbstractActivityTest<ShowErrorOnBackTabActivity> {

    @Test
    public void shouldStayOnTheFirstStepWhenVerificationFailsAndShowError() {
        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(0);
        checkTabState(0, StepTabStateMatcher.TabState.WARNING);
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
        SpoonScreenshotAction.perform(getScreenshotTag(3, "Last step test"));
    }

    @Test
    public void shouldGoToTheNextStepAndClearWarningWhenStepVerificationSucceeds() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(1);
        checkTabState(0, StepTabStateMatcher.TabState.DONE);
        checkTabState(1, StepTabStateMatcher.TabState.ACTIVE);
        checkTabState(2, StepTabStateMatcher.TabState.INACTIVE);
        SpoonScreenshotAction.perform(getScreenshotTag(4, "Clear warning on success test"));
    }

    @Test
    public void shouldNotClearWarningWhenGoingBackToPreviousStep() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickBack());

        //then
        checkCurrentStepIs(0);
        checkTabState(0, StepTabStateMatcher.TabState.ACTIVE);
        checkTabState(1, StepTabStateMatcher.TabState.WARNING);
        checkTabState(2, StepTabStateMatcher.TabState.INACTIVE);
        SpoonScreenshotAction.perform(getScreenshotTag(5, "Not cleared warning on Back test"));
    }

}
