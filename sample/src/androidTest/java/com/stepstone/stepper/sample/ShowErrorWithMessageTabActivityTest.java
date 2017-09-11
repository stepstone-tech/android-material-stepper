package com.stepstone.stepper.sample;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;

import com.stepstone.stepper.internal.widget.StepTabStateMatcher;
import com.stepstone.stepper.sample.test.action.SpoonScreenshotAction;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkCurrentStepIs;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkTabState;
import static com.stepstone.stepper.sample.test.matcher.CommonMatchers.checkTabSubtitle;
import static com.stepstone.stepper.test.StepperNavigationActions.clickNext;
import static org.hamcrest.Matchers.allOf;

/**
 * Performs tests on a tabbed stepper i.e. the one with {@code ms_stepperType="tabs"}.
 * This also tests if the errors are shown in the tabs with a message.
 *
 * @author Piotr Zawadzki
 */
@LargeTest
public class ShowErrorWithMessageTabActivityTest extends AbstractActivityTest<ShowErrorWithMessageTabActivity> {

    private static final String ERROR_MESSAGE = "Click 2 more times!";
    private static final String STEP_SUBTITLE = "Optional";

    @Test
    public void shouldStayOnTheFirstStepWhenVerificationFailsAndShowErrorWithMessage() {
        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(0);
        checkTabState(0, StepTabStateMatcher.TabState.WARNING);
        checkTabState(1, StepTabStateMatcher.TabState.INACTIVE);
        checkTabState(2, StepTabStateMatcher.TabState.INACTIVE);
        checkTabSubtitle(0, withText(ERROR_MESSAGE));
        checkTabSubtitle(1, withText(STEP_SUBTITLE));
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
        checkTabSubtitle(0, withEffectiveVisibility(ViewMatchers.Visibility.GONE));
        checkTabSubtitle(1, withText(STEP_SUBTITLE));
        SpoonScreenshotAction.perform(getScreenshotTag(2, "Verification success test"));
    }

    @Test
    public void shouldClearErrorMessageWhenVerificationSucceeds() {
        //given
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(1);
        checkTabSubtitle(0, withEffectiveVisibility(ViewMatchers.Visibility.GONE));
        checkTabSubtitle(1, withText(STEP_SUBTITLE));
        SpoonScreenshotAction.perform(getScreenshotTag(3, "Clear error message test"));
    }

    @Test
    public void shouldChangeSubtitleToErrorMessage() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(1);
        checkTabSubtitle(0, withEffectiveVisibility(ViewMatchers.Visibility.GONE));
        checkTabSubtitle(1, withText(ERROR_MESSAGE));
        SpoonScreenshotAction.perform(getScreenshotTag(4, "Change subtitle to error message test"));
    }

    @Test
    public void shouldChangeBackToSubtitleFromErrorMessage() {
        //given
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(withId(R.id.stepperLayout)).perform(clickNext());
        onView(allOf(withId(R.id.button), isCompletelyDisplayed())).perform(doubleClick());

        //when
        onView(withId(R.id.stepperLayout)).perform(clickNext());

        //then
        checkCurrentStepIs(2);
        checkTabSubtitle(0, withEffectiveVisibility(ViewMatchers.Visibility.GONE));
        checkTabSubtitle(1, withText(STEP_SUBTITLE));
        SpoonScreenshotAction.perform(getScreenshotTag(5, "Change back to subtitle test"));
    }

}
