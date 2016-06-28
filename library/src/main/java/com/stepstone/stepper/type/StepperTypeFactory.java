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

import android.util.Log;

import com.stepstone.stepper.StepperLayout;

/**
 * Factory class for creating stepper types.
 */
public class StepperTypeFactory {

    private static final String TAG = StepperTypeFactory.class.getSimpleName();

    /**
     * Creates a stepper type for provided arguments.
     * @param stepType step type, one of <code>attrs - ms_stepperType</code>
     * @param stepperLayout stepper layout to use with this stepper type
     * @return a stepper type
     */
    public static AbstractStepperType createType(int stepType, StepperLayout stepperLayout) {
        switch (stepType) {
            case AbstractStepperType.DOTS:
                return new DotsStepperType(stepperLayout);
            case AbstractStepperType.PROGRESS_BAR:
                return new ProgressBarStepperType(stepperLayout);
            case AbstractStepperType.TABS:
                return new TabsStepperType(stepperLayout);
            default:
                Log.e(TAG, "Unsupported type: " + stepType);
                throw new IllegalArgumentException("Unsupported type: " + stepType);
        }
    }
}
