package com.stepstone.stepper.adapter;

import android.support.v4.view.PagerAdapter;

import com.stepstone.stepper.Step;

/**
 * Created by leonardo on 18/12/16.
 */

/**
 * A base adapter class which returns step to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 * This class is intended to be inherited if you need to use {@link com.stepstone.stepper.StepperLayout} without fragments.
 * Otherwise, you should use {@link AbstractStepAdapter}
 */
public abstract class AbstractStepAdapter<T extends Step> extends PagerAdapter implements StepAdapter<T> {

    /** {@inheritDoc} */
    @Override
    public int getNextButtonText(int position) {
        return DEFAULT_NEXT_BUTTON_TEXT;
    }

    /** {@inheritDoc} */
    @Override
    public PagerAdapter getAdapter() {
        return this;
    }
}
