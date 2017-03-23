package com.stepstone.stepper.test.assertion;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;

import org.assertj.android.api.Assertions;
import org.assertj.android.api.view.ViewAssert;
import org.assertj.android.api.widget.AbstractLinearLayoutAssert;

/**
 * @author Piotr Zawadzki
 */
public class StepperLayoutAssert extends AbstractLinearLayoutAssert<StepperLayoutAssert, StepperLayout> {

    public static StepperLayoutAssert assertThat(@NonNull StepperLayout actual) {
        return new StepperLayoutAssert(actual);
    }

    protected StepperLayoutAssert(StepperLayout actual) {
        super(actual, StepperLayoutAssert.class);
    }

    public StepperLayoutAssert hasHorizontalProgressBarShown() {
        hasNotNullChildView(R.id.ms_stepProgressBar)
                .isVisible();
        return this;
    }

    public StepperLayoutAssert hasHorizontalProgressBarHidden() {
        hasNotNullChildView(R.id.ms_stepProgressBar)
                .isGone();
        return this;
    }

    public StepperLayoutAssert hasDottedProgressBarShown() {
        hasNotNullChildView(R.id.ms_stepDottedProgressBar)
                .isVisible();
        return this;
    }

    public StepperLayoutAssert hasDottedProgressBarHidden() {
        hasNotNullChildView(R.id.ms_stepDottedProgressBar)
                .isGone();
        return this;
    }

    public StepperLayoutAssert hasTabsShown() {
        hasNotNullChildView(R.id.ms_stepTabsContainer)
                .isVisible();
        return this;
    }

    public StepperLayoutAssert hasTabsHidden() {
        hasNotNullChildView(R.id.ms_stepTabsContainer)
                .isGone();
        return this;
    }

    private ViewAssert hasNotNullChildView(@IdRes int childId) {
        View progressBar = actual.findViewById(childId);
        return Assertions.assertThat(progressBar)
                .isNotNull();
    }
}
