package com.stepstone.stepper.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;

import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * A base adapter class which returns step to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 * This class is intended to be inherited if you need to use {@link com.stepstone.stepper.StepperLayout} without fragments.
 * Otherwise, you should use {@link AbstractFragmentStepAdapter}
 */
public abstract class AbstractStepAdapter
        extends PagerAdapter
        implements StepAdapter {

    @NonNull
    protected final Context context;

    public AbstractStepAdapter(@NonNull Context context) {
        this.context = context;
    }

    /** {@inheritDoc} */
    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context).create();
    }

    /** {@inheritDoc} */
    @Override
    public final PagerAdapter getPagerAdapter() {
        return this;
    }
}
