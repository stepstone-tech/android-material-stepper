/*
Copyright 2016 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.stepstone.stepper.type;

import android.support.annotation.NonNull;
import android.view.View;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.adapter.AbstractStepAdapter;
import com.stepstone.stepper.internal.DottedProgressBar;

/**
 * Stepper type which displays mobile step dots.
 */
public class DotsStepperType extends AbstractStepperType {

    private final DottedProgressBar mDottedProgressBar;

    public DotsStepperType(StepperLayout stepperLayout) {
        super(stepperLayout);
        mDottedProgressBar = (DottedProgressBar) stepperLayout.findViewById(R.id.ms_stepDottedProgressBar);

        mDottedProgressBar.setSelectedColor(getSelectedColor());
        mDottedProgressBar.setUnselectedColor(getUnselectedColor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStepSelected(int newStepPosition) {
        mDottedProgressBar.setCurrent(newStepPosition, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewAdapter(@NonNull AbstractStepAdapter stepAdapter) {
        final int stepCount = stepAdapter.getCount();
        mDottedProgressBar.setDotCount(stepCount);
        mDottedProgressBar.setVisibility(stepCount > 1 ? View.VISIBLE : View.GONE);
    }
}
