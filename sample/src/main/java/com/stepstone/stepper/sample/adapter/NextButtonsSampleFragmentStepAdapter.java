package com.stepstone.stepper.sample.adapter;

import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.sample.R;
import com.stepstone.stepper.sample.step.fragment.StepFragmentSample;

public class NextButtonsSampleFragmentStepAdapter extends AbstractFragmentStepAdapter {

    public NextButtonsSampleFragmentStepAdapter(FragmentManager fm) {
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

    @StringRes
    @Override
    public int getNextButtonText(int position) {
        switch (position) {
            case 0:
                return R.string.ms_next;
            case 1:
                return R.string.go_to_summary;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }
}