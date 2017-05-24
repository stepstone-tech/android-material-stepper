package com.stepstone.stepper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.test.assertion.StepperLayoutAssert;
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner;
import com.stepstone.stepper.viewmodel.StepViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import static com.stepstone.stepper.test.assertion.StepperLayoutAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner.class)
public class StepperLayoutSanityTest {

    private static final String TYPE_PROGRESS_BAR = "progress_bar";
    private static final String TYPE_DOTS = "dots";
    private static final String TYPE_TABS = "tabs";

    private static final String ORIENTATION_HORIZONTAL = "horizontal";

    private StepperLayoutActivity activity;

    private StepperLayout stepperLayout;

    @Test
    public void stepper_with_progress_bar_without_adapter_should_have_all_type_specific_indicators_hidden() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_PROGRESS_BAR);

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet);

        //then
        assertStepperLayout()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
                .hasTabsHidden();
    }

    @Test
    public void stepper_with_dots_without_adapter_should_have_all_type_specific_indicators_hidden() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_DOTS);

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet);

        //then
        assertStepperLayout()
                .hasDottedProgressBarHidden()
                .hasHorizontalProgressBarHidden()
                .hasTabsHidden();
    }

    @Test
    public void stepper_with_tabs_without_adapter_should_have_all_type_specific_indicators_hidden() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_TABS);

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet);

        //then
        assertStepperLayout()
                .hasTabsHidden()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden();
    }

    @Test
    public void should_show_horizontal_progress_bar_when_adapter_is_set_for_progress_bar_type() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_PROGRESS_BAR);

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet);

        //then
        assertStepperLayout()
                .hasHorizontalProgressBarShown()
                .hasDottedProgressBarHidden()
                .hasTabsHidden();
        assertFirstFragmentWasNotifiedAboutBeingSelected();
    }

    @Test
    public void should_show_dotted_progress_bar_when_adapter_is_set_for_dots_type() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_DOTS);

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet);

        //then
        assertStepperLayout()
                .hasDottedProgressBarShown()
                .hasHorizontalProgressBarHidden()
                .hasTabsHidden();
        assertFirstFragmentWasNotifiedAboutBeingSelected();
    }

    @Test
    public void should_show_tabs_when_adapter_is_set_for_tabs_type() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_TABS);

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet);

        //then
        assertStepperLayout()
                .hasTabsShown()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden();
        assertFirstFragmentWasNotifiedAboutBeingSelected();
    }

    @Test
    public void should_ignore_horizontal_orientation_if_provided_from_attributes() {
        //given
        AttributeSet attributeSet = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, TYPE_DOTS)
                .addAttribute(android.R.attr.orientation, ORIENTATION_HORIZONTAL)
                .build();

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet);

        //then
        assertStepperLayout().hasOrientation(LinearLayout.VERTICAL);
    }

    @Test
    public void should_ignore_horizontal_orientation_if_provided_programmatically() {
        //given
        AttributeSet attributeSet = createAttributeSetWithStepperType(TYPE_DOTS);
        stepperLayout = createStepperLayoutInActivity(attributeSet);

        //when
        stepperLayout.setOrientation(LinearLayout.HORIZONTAL);

        //then
        assertStepperLayout().hasOrientation(LinearLayout.VERTICAL);
    }

    @NonNull
    private AttributeSet createAttributeSetWithStepperType(String stepperType) {
        return Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, stepperType)
                .build();
    }

    @NonNull
    private StepperLayout createStepperLayoutInActivity(@NonNull AttributeSet attributeSet) {
        activity = ActivityController.of(Robolectric.getShadowsAdapter(), new StepperLayoutActivity().withStepperLayoutAttributes(attributeSet))
                .setup()
                .get();
        return activity.stepperLayout;
    }

    @NonNull
    private StepperLayout createStepperLayoutWithAdapterSetInActivity(@NonNull AttributeSet attributeSet) {
        activity = ActivityController.of(Robolectric.getShadowsAdapter(), new StepperLayoutWithAdapterActivity().withStepperLayoutAttributes(attributeSet))
                .setup()
                .get();
        return activity.stepperLayout;
    }

    @NonNull
    private StepperLayoutAssert assertStepperLayout() {
        return assertThat(stepperLayout);
    }

    private void assertFirstFragmentWasNotifiedAboutBeingSelected() {
        SpyStepAdapter stepAdapter = (SpyStepAdapter) stepperLayout.getAdapter();
        Step firstStep = stepAdapter.steps.get(0);
        assertNotNull("Step not found", firstStep);
        verify(firstStep).onSelected();
    }

    /**
     * A {@link StepperLayoutActivity} which also sets an adapter in {@link #onCreate(Bundle)}.
     */
    private static class StepperLayoutWithAdapterActivity extends StepperLayoutActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SpyStepAdapter stepAdapter = new SpyStepAdapter(getSupportFragmentManager(), this);
            stepperLayout.setAdapter(stepAdapter);
        }
    }

    /**
     * An Activity which created a StepperLayout in {@link #onCreate(Bundle)}.
     */
    private static class StepperLayoutActivity extends FragmentActivity {

        AttributeSet stepperLayoutAttributeSet;

        StepperLayout stepperLayout;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stepperLayout = new StepperLayout(this, stepperLayoutAttributeSet);
            stepperLayout.setLayoutParams(layoutParams);
            setContentView(stepperLayout);
        }

        StepperLayoutActivity withStepperLayoutAttributes(AttributeSet attributeSet) {
            this.stepperLayoutAttributeSet = attributeSet;
            return this;
        }
    }

    /**
     * Creates Spy fragments which can be later verified.
     */
    private static class SpyStepAdapter extends AbstractFragmentStepAdapter {

        SparseArray<Step> steps = new SparseArray<>();

        SpyStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
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
            DummyStepFragment stepFragment = spy(new DummyStepFragment());
            steps.put(position, stepFragment);
            return stepFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    /**
     * A dummy fragment with no view.
     */
    public static class DummyStepFragment extends Fragment implements Step {

        @Override
        public VerificationError verifyStep() {
            return null;
        }

        @Override
        public void onSelected() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return null;
        }

        @Override
        public void onError(@NonNull VerificationError error) {
        }
    }

}