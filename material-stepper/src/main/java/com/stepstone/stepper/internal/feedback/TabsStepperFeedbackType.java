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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static com.stepstone.stepper.internal.util.AnimationUtil.ALPHA_INVISIBLE;
import static com.stepstone.stepper.internal.util.AnimationUtil.ALPHA_OPAQUE;

/**
 * Feedback stepper type which displays a progress message instead of the tabs.
 */
@RestrictTo(LIBRARY)
public class TabsStepperFeedbackType implements StepperFeedbackType {

    private final float mProgressMessageTranslationWhenHidden;

    private boolean mTabNavigationEnabled;

    private TextView mProgressMessageTextView;

    private View mTabs;

    private StepperLayout mStepperLayout;

    public TabsStepperFeedbackType(@NonNull StepperLayout stepperLayout) {
        mProgressMessageTranslationWhenHidden = stepperLayout.getResources().getDimension(R.dimen.ms_progress_message_translation_when_hidden);
        mProgressMessageTextView = (TextView) stepperLayout.findViewById(R.id.ms_stepTabsProgressMessage);
        mTabs = stepperLayout.findViewById(R.id.ms_stepTabsScrollView);
        mStepperLayout = stepperLayout;
        mProgressMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(@NonNull String progressMessage) {
        mTabNavigationEnabled = mStepperLayout.isTabNavigationEnabled();
        setTabNavigationEnabled(false);
        mProgressMessageTextView.setText(progressMessage);
        mProgressMessageTextView.animate()
                .setStartDelay(PROGRESS_ANIMATION_DURATION)
                .alpha(ALPHA_OPAQUE)
                .translationY(0.0f)
                .setDuration(PROGRESS_ANIMATION_DURATION);
        mTabs.animate()
                .alpha(ALPHA_INVISIBLE)
                .setStartDelay(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(PROGRESS_ANIMATION_DURATION);
    }

    @Override
    public void hideProgress() {
        setTabNavigationEnabled(mTabNavigationEnabled);

        mProgressMessageTextView.animate()
                .setStartDelay(0)
                .alpha(ALPHA_INVISIBLE)
                .translationY(mProgressMessageTranslationWhenHidden)
                .setDuration(PROGRESS_ANIMATION_DURATION);
        mTabs.animate()
                .alpha(ALPHA_OPAQUE)
                .setStartDelay(PROGRESS_ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(PROGRESS_ANIMATION_DURATION);
    }

    private void setTabNavigationEnabled(boolean tabNavigationEnabled) {
        mStepperLayout.setTabNavigationEnabled(tabNavigationEnabled);
    }
}
