package com.stepstone.stepper.sample;

import android.support.test.filters.LargeTest;

import com.stepstone.stepper.sample.test.action.SpoonScreenshotAction;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkBackButtonColor;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCompleteButtonColor;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCompleteButtonShown;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCurrentStepIs;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkNextButtonColor;
import static com.stepstone.stepper.test.StepperNavigationActions.clickComplete;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static org.hamcrest.Matchers.allOf;

/**
 * Performs tests on a stepper on which we change the button colors in code.
 *
 * @author Piotr Zawadzki
 */
@LargeTest
public class SetButtonColorProgrammaticallyActivityTest extends AbstractActivityTest<SetButtonColorProgrammaticallyActivity> {

    @Test
    public void shouldStayOnTheFirstStepWhenVerificationFails() {
        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        SpoonScreenshotAction.perform(getScreenshotTag(1, "Verification failure test"));
        checkCurrentStepIs(0);
        checkNextButtonColor(R.color.verification_failed_color);
    }

    @Test
    public void shouldGoToTheNextStepWhenVerificationSucceeds() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        SpoonScreenshotAction.perform(getScreenshotTag(2, "Verification success test"));
        checkCurrentStepIs(1);
        checkNextButtonColor(R.color.ms_black);
        checkBackButtonColor(R.color.ms_white);
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
        SpoonScreenshotAction.perform(getScreenshotTag(3, "Last step test"));
        checkCurrentStepIs(2);
        checkCompleteButtonShown();
        checkCompleteButtonColor(R.color.verification_failed_color);
        checkBackButtonColor(R.color.ms_black);
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
        SpoonScreenshotAction.perform(getScreenshotTag(4, "Completion test"));
        checkCurrentStepIs(2);
        checkCompleteButtonColor(R.color.ms_black);
        checkBackButtonColor(R.color.ms_black);
    }

}
