package com.stepstone.stepper.sample;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;

import com.stepstone.stepper.sample.test.action.SpoonScreenshotAction;

import org.junit.Test;

import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Performs sanity tests i.e. open each sample app Activity and makes a screenshot of the screen.
 *
 * @author Piotr Zawadzki
 */
@LargeTest
public class SanityTest extends AbstractActivityTest<MainActivity> {

    @Test
    public void shouldOpenDefaultDotsActivity() {
        //when
        clickRowWithText(R.string.default_dots);

        //then
        intended(hasComponent(DefaultDotsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(1, R.string.default_dots));
    }

    @Test
    public void shouldOpenStyledDotsActivity() {
        //when
        clickRowWithText(R.string.styled_dots);

        //then
        intended(hasComponent(StyledDotsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(2, R.string.styled_dots));
    }

    @Test
    public void shouldOpenThemedDotsActivity() {
        //when
        clickRowWithText(R.string.themed_dots);

        //then
        intended(hasComponent(ThemedDotsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(3, R.string.themed_dots));
    }

    @Test
    public void shouldOpenDefaultProgressBarActivity() {
        //when
        clickRowWithText(R.string.default_progress_bar);

        //then
        intended(hasComponent(DefaultProgressBarActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(4, R.string.default_progress_bar));
    }

    @Test
    public void shouldOpenStyledProgressBarActivity() {
        //when
        clickRowWithText(R.string.styled_progress_bar);

        //then
        intended(hasComponent(StyledProgressBarActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(5, R.string.styled_progress_bar));
    }

    @Test
    public void shouldOpenDefaultTabsActivity() {
        //when
        clickRowWithText(R.string.default_tabs);

        //then
        intended(hasComponent(DefaultTabsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(6, R.string.default_tabs));
    }

    @Test
    public void shouldOpenStyledTabsActivity() {
        //when
        clickRowWithText(R.string.styled_tabs);

        //then
        intended(hasComponent(StyledTabsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(7, R.string.styled_tabs));
    }

    @Test
    public void shouldOpenDefaultNoneActivity() {
        //when
        clickRowWithText(R.string.default_none);

        //then
        intended(hasComponent(DefaultNoneActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(8, R.string.default_none));
    }

    @Test
    public void shouldOpenErrorOnTabsActivity() {
        //when
        clickRowWithText(R.string.error_tabs);

        //then
        intended(hasComponent(ShowErrorTabActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(9, R.string.error_tabs));
    }

    @Test
    public void shouldOpenColoredErrorOnTabsActivity() {
        //when
        clickRowWithText(R.string.error_color_tabs);

        //then
        intended(hasComponent(ShowErrorCustomColorTabActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(10, R.string.error_color_tabs));
    }

    @Test
    public void shouldOpenErrorOnTabsOnBackActivity() {
        //when
        clickRowWithText(R.string.error_back_tabs);

        //then
        intended(hasComponent(ShowErrorOnBackTabActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(11, R.string.error_back_tabs));
    }

    @Test
    public void shouldOpenErrorOnTabsWithMessageActivity() {
        //when
        clickRowWithText(R.string.error_with_message_tabs);

        //then
        intended(hasComponent(ShowErrorWithMessageTabActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(12, R.string.error_with_message_tabs));
    }

    @Test
    public void shouldOpenCombinationActivity() {
        //when
        clickRowWithText(R.string.combination);

        //then
        intended(hasComponent(CombinationActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(13, R.string.combination));
    }

    @Test
    public void shouldOpenCustomPageTransformerActivity() {
        //when
        clickRowWithText(R.string.custom_page_transformer);

        //then
        intended(hasComponent(CustomPageTransformerActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(14, R.string.custom_page_transformer));
    }

    @Test
    public void shouldOpenDelayedTransitionActivity() {
        //when
        clickRowWithText(R.string.delayed_transition);

        //then
        intended(hasComponent(DelayedTransitionStepperActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(15, R.string.delayed_transition));
    }

    @Test
    public void shouldOpenStepperFeedbackActivity() {
        //when
        clickRowWithText(R.string.stepper_feedback);

        //then
        intended(hasComponent(StepperFeedbackActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(16, R.string.stepper_feedback));
    }

    @Test
    public void shouldOpenCustomNavigationButtonsActivity() {
        //when
        clickRowWithText(R.string.custom_navigation_buttons);

        //then
        intended(hasComponent(CustomNavigationButtonsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(17, R.string.custom_navigation_buttons));
    }

    @Test
    public void shouldOpenReturnButtonActivity() {
        //when
        clickRowWithText(R.string.show_back_button);

        //then
        intended(hasComponent(ReturnButtonActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(18, R.string.show_back_button));
    }

    @Test
    public void shouldOpenNoFragmentsActivity() {
        //when
        clickRowWithText(R.string.no_fragments);

        //then
        intended(hasComponent(NoFragmentsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(19, R.string.no_fragments));
    }

    @Test
    public void shouldOpenProceedProgrammaticallyActivity() {
        //when
        clickRowWithText(R.string.proceed_programmatically);

        //then
        intended(hasComponent(ProceedProgrammaticallyActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(20, R.string.proceed_programmatically));
    }

    @Test
    public void shouldOpenPassDataBetweenStepsActivity() {
        //when
        clickRowWithText(R.string.passing_data_between_steps);

        //then
        intended(hasComponent(PassDataBetweenStepsActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(21, R.string.passing_data_between_steps));
    }

    @Test
    public void shouldOpenDisabledTabNavigationActivity() {
        //when
        clickRowWithText(R.string.disabled_tab_navigation);

        //then
        intended(hasComponent(DisabledTabNavigationActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(22, R.string.disabled_tab_navigation));
    }

    @Test
    public void shouldOpenHiddenBottomNavigationActivity() {
        //when
        clickRowWithText(R.string.hidden_bottom_navigation);

        //then
        intended(hasComponent(HiddenBottomNavigationActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(23, R.string.hidden_bottom_navigation));
    }

    @Test
    public void shouldOpenCustomStepperLayoutThemeActivity() {
        //when
        clickRowWithText(R.string.custom_stepperlayout_theme);

        //then
        intended(hasComponent(CustomStepperLayoutThemeActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(24, R.string.custom_stepperlayout_theme));
    }

    @Test
    public void shouldOpenSetButtonColorProgrammaticallyActivity() {
        //when
        clickRowWithText(R.string.set_button_color_programmatically);

        //then
        intended(hasComponent(SetButtonColorProgrammaticallyActivity.class.getName()));
        SpoonScreenshotAction.perform(getScreenshotTag(25, R.string.set_button_color_programmatically));
    }

    @NonNull
    private String getScreenshotTag(int position, @StringRes int titleResId) {
        return String.format(Locale.ENGLISH,"%02d", position) + ". " + intentsTestRule.getActivity().getString(titleResId);
    }

    private void clickRowWithText(@StringRes int stringResId) {
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItem(withChild(withText(stringResId)), click()));
    }
}
