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

import com.stepstone.stepper.StepperLayout;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Feedback stepper type which disabled the buttons in the bottom navigation when an operation is in progress.
 */
@RestrictTo(LIBRARY)
public class DisabledBottomNavigationStepperFeedbackType implements StepperFeedbackType {

    private StepperLayout mStepperLayout;

    public DisabledBottomNavigationStepperFeedbackType(@NonNull StepperLayout stepperLayout) {
        mStepperLayout = stepperLayout;
    }

    @Override
    public void showProgress(@NonNull String progressMessage) {
        setButtonsEnabled(false);
    }

    @Override
    public void hideProgress() {
        setButtonsEnabled(true);
    }

    private void setButtonsEnabled(boolean enabled) {
        mStepperLayout.setNextButtonEnabled(enabled);
        mStepperLayout.setCompleteButtonEnabled(enabled);
        mStepperLayout.setBackButtonEnabled(enabled);
    }

}
