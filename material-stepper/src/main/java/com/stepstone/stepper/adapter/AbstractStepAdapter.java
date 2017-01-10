package com.stepstone.stepper.adapter;

import android.support.v4.view.PagerAdapter;

/**
 * A base adapter class which returns step to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 * This class is intended to be inherited if you need to use {@link com.stepstone.stepper.StepperLayout} without fragments.
 * Otherwise, you should use {@link AbstractFragmentStepAdapter}
 */
public abstract class AbstractStepAdapter extends PagerAdapter implements StepAdapter {

    /** {@inheritDoc} */
    @Override
    public int getNextButtonText(int position) {
        return DEFAULT_NEXT_BUTTON_TEXT;
    }

    /** {@inheritDoc} */
    @Override
    public final PagerAdapter getPagerAdapter() {
        return this;
    }
}
