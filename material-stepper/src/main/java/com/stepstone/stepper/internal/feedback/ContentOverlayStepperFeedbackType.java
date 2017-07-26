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
import android.view.View;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static com.stepstone.stepper.internal.util.AnimationUtil.ALPHA_INVISIBLE;
import static com.stepstone.stepper.internal.util.AnimationUtil.ALPHA_OPAQUE;

/**
 * Feedback stepper type which shows a dimmed overlay over the content.
 */
@RestrictTo(LIBRARY)
public class ContentOverlayStepperFeedbackType implements StepperFeedbackType {

    @NonNull
    private final View mOverlayView;

    public ContentOverlayStepperFeedbackType(@NonNull StepperLayout stepperLayout) {
        mOverlayView = stepperLayout.findViewById(R.id.ms_stepPagerOverlay);
        mOverlayView.setVisibility(View.VISIBLE);
        mOverlayView.setAlpha(ALPHA_INVISIBLE);
        final int contentOverlayBackground = stepperLayout.getContentOverlayBackground();
        if (contentOverlayBackground != 0)  {
            mOverlayView.setBackgroundResource(contentOverlayBackground);
        }
    }

    @Override
    public void showProgress(@NonNull String progressMessage) {
        mOverlayView.animate()
                .alpha(ALPHA_OPAQUE)
                .setDuration(PROGRESS_ANIMATION_DURATION);
    }

    @Override
    public void hideProgress() {
        mOverlayView.animate()
                .alpha(ALPHA_INVISIBLE)
                .setDuration(PROGRESS_ANIMATION_DURATION);
    }
}
