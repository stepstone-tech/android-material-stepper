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

import java.util.ArrayList;
import java.util.List;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * A stepper feedback type which is a composition of other feedback type, which allows to select only a group of feedback types.
 * See Stepper feedback section in https://material.io/guidelines/components/steppers.html#steppers-types-of-steppers
 */
@RestrictTo(LIBRARY)
public class StepperFeedbackTypeComposite implements StepperFeedbackType {

    private List<StepperFeedbackType> mChildren = new ArrayList<>();

    @Override
    public void showProgress(@NonNull String progressMessage) {
        for (StepperFeedbackType child : mChildren) {
            child.showProgress(progressMessage);
        }
    }

    @Override
    public void hideProgress() {
        for (StepperFeedbackType child : mChildren) {
            child.hideProgress();
        }
    }

    /**
     * Adds a child component to this composite.
     * @param component child to add
     */
    public void addComponent(StepperFeedbackType component) {
        mChildren.add(component);
    }

}