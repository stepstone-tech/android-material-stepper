package com.stepstone.stepper.adapter;

import android.support.v4.view.PagerAdapter;

import com.stepstone.stepper.Step;

/**
 * Created by leonardo on 18/12/16.
 */

public abstract class AbstractStepAdapter<T extends Step> extends PagerAdapter implements StepAdapter<T> {

    private static final int DEFAULT_NEXT_BUTTON_TEXT = -1;

    @Override
    public int getNextButtonText(int position) {
        return DEFAULT_NEXT_BUTTON_TEXT;
    }

    @Override
    public PagerAdapter getAdapter() {
        return this;
    }
}
