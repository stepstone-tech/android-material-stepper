package com.stepstone.stepper.sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.sample.step.fragment.PassDataBetweenStepsFirstStepFragment;
import com.stepstone.stepper.sample.step.fragment.PassDataBetweenStepsSecondStepFragment;

public class PassDataBetweenStepsFragmentStepAdapter extends AbstractFragmentStepAdapter {

    public PassDataBetweenStepsFragmentStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                return PassDataBetweenStepsFirstStepFragment.newInstance();
            case 1:
                return PassDataBetweenStepsSecondStepFragment.newInstance();
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}