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

import android.support.annotation.RestrictTo;

import com.stepstone.stepper.StepperLayout;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Factory class for creating feedback stepper types.
 */
@RestrictTo(LIBRARY)
public class StepperFeedbackTypeFactory {

    /**
     * Creates a stepper feedback type for provided arguments.
     * It can be a composition of several feedback types depending on the provided flags.
     *
     * @param feedbackTypeMask step feedback type mask, should contain one or more from {@link StepperFeedbackType}
     * @param stepperLayout stepper layout to use with the chosen stepper feedback type(s)
     * @return a stepper feedback type
     */
    public static StepperFeedbackType createType(int feedbackTypeMask, StepperLayout stepperLayout) {

        StepperFeedbackTypeComposite stepperFeedbackTypeComposite = new StepperFeedbackTypeComposite();

        if ((feedbackTypeMask & StepperFeedbackType.NONE) != 0) {
            //Add no more components if NONE type is selected
            return stepperFeedbackTypeComposite;
        }

        if ((feedbackTypeMask & StepperFeedbackType.TABS) != 0) {
            stepperFeedbackTypeComposite.addComponent(new TabsStepperFeedbackType(stepperLayout));
        }

        if ((feedbackTypeMask & StepperFeedbackType.CONTENT) != 0) {
            stepperFeedbackTypeComposite.addComponent(new ContentStepperFeedbackType(stepperLayout));
        }

        if ((feedbackTypeMask & StepperFeedbackType.DISABLED_BOTTOM_NAVIGATION) != 0) {
            stepperFeedbackTypeComposite.addComponent(new DisabledBottomNavigationStepperFeedbackType(stepperLayout));
        }

        return stepperFeedbackTypeComposite;
    }
}
