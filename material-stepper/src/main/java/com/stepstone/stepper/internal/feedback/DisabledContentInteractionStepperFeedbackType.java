/*
Copyright 2017 StepStone Services

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

package com.stepstone.stepper.internal.feedback;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.internal.widget.StepViewPager;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Feedback stepper type which intercepts touch events on the steps' content and ignores them.
 */
@RestrictTo(LIBRARY)
public class DisabledContentInteractionStepperFeedbackType implements StepperFeedbackType {

    @NonNull
    private final StepViewPager mStepPager;

    public DisabledContentInteractionStepperFeedbackType(@NonNull StepperLayout stepperLayout) {
        mStepPager = (StepViewPager) stepperLayout.findViewById(R.id.ms_stepPager);
    }

    @Override
    public void showProgress(@NonNull String progressMessage) {
        setContentInteractionEnabled(false);
    }

    @Override
    public void hideProgress() {
        setContentInteractionEnabled(true);
    }

    private void setContentInteractionEnabled(boolean enabled) {
        mStepPager.setBlockTouchEventsFromChildrenEnabled(!enabled);
    }

}
