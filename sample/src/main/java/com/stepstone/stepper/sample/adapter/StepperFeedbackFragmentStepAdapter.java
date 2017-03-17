package com.stepstone.stepper.sample.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.sample.R;
import com.stepstone.stepper.sample.step.fragment.StepperFeedbackStepFragment;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class StepperFeedbackFragmentStepAdapter extends AbstractFragmentStepAdapter {

    public StepperFeedbackFragmentStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        return StepperFeedbackStepFragment.newInstance(position);
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context)
                .setTitle(R.string.tab_title)
                .create();
    }

    @Override
    public int getCount() {
        return 3;
    }
}