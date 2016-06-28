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

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.adapter.AbstractStepAdapter;

/**
 * A base stepper type all stepper types must extend.
 */
public abstract class AbstractStepperType {

    /**
     * Mobile step dots
     */
    public static final int DOTS = 0x01;

    /**
     * Mobile step progress bar
     */
    public static final int PROGRESS_BAR = 0x02;

    /**
     * Horizontal stepper with tabs
     */
    public static final int TABS = 0x03;

    protected final StepperLayout stepperLayout;

    public AbstractStepperType(StepperLayout stepperLayout) {
        this.stepperLayout = stepperLayout;
    }

    /**
     * Called when a step gets selected as the new current step
     * @param newStepPosition new current step position
     */
    public abstract void onStepSelected(int newStepPosition);

    /**
     * Called when {@link StepperLayout}'s adapter gets changed
     * @param stepAdapter new stepper adapter
     */
    public abstract void onNewAdapter(@NonNull AbstractStepAdapter<?> stepAdapter);

    @ColorInt
    protected int getSelectedColor() {
        return stepperLayout.getSelectedColor();
    }

    @ColorInt
    protected int getUnselectedColor() {
        return stepperLayout.getUnselectedColor();
    }

}