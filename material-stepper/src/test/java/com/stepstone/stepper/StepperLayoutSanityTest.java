package com.stepstone.stepper;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner;
import com.stepstone.stepper.viewmodel.StepViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static com.stepstone.stepper.test.assertion.StepperLayoutAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner.class)
public class StepperLayoutSanityTest {

    private static final String TYPE_PROGRESS_BAR = "progress_bar";
    private static final String TYPE_DOTS = "dots";
    private static final String TYPE_TABS = "tabs";

    private static final String ORIENTATION_HORIZONTAL = "horizontal";

    FragmentActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(DummyActivity.class);
    }

    @Test
    public void stepper_with_progress_bar_without_adapter_should_have_all_type_specific_indicators_hidden() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_PROGRESS_BAR);

        //when
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //then
        assertThat(stepperLayout)
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
                .hasTabsHidden();
    }

    @Test
    public void stepper_with_dots_without_adapter_should_have_all_type_specific_indicators_hidden() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_DOTS);

        //when
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //then
        assertThat(stepperLayout)
                .hasDottedProgressBarHidden()
                .hasHorizontalProgressBarHidden()
                .hasTabsHidden();
    }

    @Test
    public void stepper_with_tabs_without_adapter_should_have_all_type_specific_indicators_hidden() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_TABS);

        //when
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //then
        assertThat(stepperLayout)
                .hasTabsHidden()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden();
    }

    @Test
    public void should_show_horizontal_progress_bar_when_adapter_is_set_for_progress_bar_type() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_PROGRESS_BAR);
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //when
        whenAdapterIsSet(stepperLayout);

        //then
        assertThat(stepperLayout)
                .hasHorizontalProgressBarShown()
                .hasDottedProgressBarHidden()
                .hasTabsHidden();
    }

    @Test
    public void should_show_dotted_progress_bar_when_adapter_is_set_for_dots_type() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_DOTS);
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //when
        whenAdapterIsSet(stepperLayout);

        //then
        assertThat(stepperLayout)
                .hasDottedProgressBarShown()
                .hasHorizontalProgressBarHidden()
                .hasTabsHidden();
    }

    @Test
    public void should_show_tabs_when_adapter_is_set_for_tabs_type() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_TABS);
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //when
        whenAdapterIsSet(stepperLayout);

        //then
        assertThat(stepperLayout)
                .hasTabsShown()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden();
    }

    @Test
    public void should_ignore_horizontal_orientation_if_provided_from_attributes() {
        //given
        AttributeSet attributeSet = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, TYPE_DOTS)
                .addAttribute(android.R.attr.orientation, ORIENTATION_HORIZONTAL)
                .build();

        //when
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //then
        assertVerticalOrientationUsed(stepperLayout);
    }

    @Test
    public void should_ignore_horizontal_orientation_if_provided_programmatically() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_DOTS);
        StepperLayout stepperLayout = new StepperLayout(activity, attributeSet);

        //when
        stepperLayout.setOrientation(LinearLayout.HORIZONTAL);

        //then
        assertVerticalOrientationUsed(stepperLayout);
    }

    private void assertVerticalOrientationUsed(StepperLayout stepperLayout) {
        assertEquals("Invalid orientation", stepperLayout.getOrientation(), LinearLayout.VERTICAL);
    }

    private void whenAdapterIsSet(StepperLayout stepperLayout) {
        stepperLayout.setAdapter(new DummyStepAdapter(activity.getSupportFragmentManager(), activity));
    }

    private AttributeSet createAttributeSetWithStepperType(String stepperType) {
        return Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, stepperType)
                .build();
    }

    public static class DummyActivity extends FragmentActivity {}

    public static class DummyStepAdapter extends AbstractFragmentStepAdapter {

        public DummyStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
            super(fm, context);
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            return new StepViewModel.Builder(context)
                    .setTitle("Dummy title")
                    .create();
        }

        @Override
        public Step createStep(int position) {
            return mock(DummyStepFragment.class);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class DummyStepFragment extends Fragment implements Step {

        @Override
        public VerificationError verifyStep() {
            return null;
        }

        @Override
        public void onSelected() {
        }

        @Override
        public void onError(@NonNull VerificationError error) {
        }
    }

}