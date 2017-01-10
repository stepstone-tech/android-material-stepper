package com.stepstone.stepper.sample.adapter;

import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.sample.R;
import com.stepstone.stepper.sample.step.fragment.StepFragmentSample;

public class SampleFragmentStepAdapter extends AbstractFragmentStepAdapter {

    public SampleFragmentStepAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                return StepFragmentSample.newInstance(R.layout.fragment_step);
            case 1:
                return StepFragmentSample.newInstance(R.layout.fragment_step2);
            case 2:
                return StepFragmentSample.newInstance(R.layout.fragment_step3);
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}