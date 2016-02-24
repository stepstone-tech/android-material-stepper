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

package com.stepstone.stepper.internal;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.stepstone.stepper.R;

/**
 * A button with an extra state to distinguish if the user can go to the next step.<br>
 * There is a custom state (<i>app:state_verification_failed</i>) which should be set to true
 * in the color state list if the tint color of the button should change in this scenario.<br>
 * The state of the button can be toggled via this view's {@link #setVerificationFailed(boolean)}
 * or in {@link com.stepstone.stepper.StepperLayout}'s
 * {@link com.stepstone.stepper.StepperLayout#setCompleteButtonVerificationFailed(boolean)} and
 * {@link com.stepstone.stepper.StepperLayout#setCompleteButtonVerificationFailed(boolean)}.
 */
public class RightNavigationButton extends AppCompatButton {

    private static final int[] STATE_VERIFICATION_FAILED = {R.attr.state_verification_failed};

    private boolean mVerificationFailed = false;

    public RightNavigationButton(Context context) {
        this(context, null);
    }

    public RightNavigationButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightNavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        if (mVerificationFailed) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_VERIFICATION_FAILED);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void setVerificationFailed(boolean verificationFailed) {
        if (this.mVerificationFailed != verificationFailed) {
            this.mVerificationFailed = verificationFailed;
            refreshDrawableState();
        }
    }

}
