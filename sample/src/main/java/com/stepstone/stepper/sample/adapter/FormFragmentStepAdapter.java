package com.stepstone.stepper.sample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.sample.step.fragment.FormStepFragment;

public class FormFragmentStepAdapter extends AbstractFragmentStepAdapter {

    public FormFragmentStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        return FormStepFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }
}