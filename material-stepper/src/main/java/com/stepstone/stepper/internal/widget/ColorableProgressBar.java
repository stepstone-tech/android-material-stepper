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

package com.stepstone.stepper.internal.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.stepstone.stepper.R;
import com.stepstone.stepper.internal.util.TintUtil;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * A {@link ProgressBar} which exposes methods for coloring primary progress and progress background colors individually.
 * It also allows to animate progress changes.
 */
@RestrictTo(LIBRARY)
public class ColorableProgressBar extends ProgressBar {

    /**
     * Interpolator used for smooth progress animations.
     */
    private static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();

    /**
     * Duration of smooth progress animations.
     */
    private static final int PROGRESS_ANIM_DURATION = 200;

    /**
     * A multiplier to be used when setting the current progress of this progress bar.
     * It is needed to animate the progress changes.
     */
    private static final int PROGRESS_RANGE_MULTIPLIER = 100;

    @ColorInt
    private int mProgressColor;

    @ColorInt
    private int mProgressBackgroundColor;

    public ColorableProgressBar(Context context) {
        this(context, null);
    }

    public ColorableProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorableProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mProgressColor = ContextCompat.getColor(context, R.color.ms_selectedColor);
        mProgressBackgroundColor = ContextCompat.getColor(context, R.color.ms_unselectedColor);
        super.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.ms_colorable_progress_bar));
        updateProgressDrawable();
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        // no-op
    }

    @Override
    public void setProgressDrawableTiled(Drawable d) {
        // no-op
    }

    @Override
    public synchronized void setMax(int max) {
        super.setMax(max * PROGRESS_RANGE_MULTIPLIER);
    }

    public void setProgressColor(@ColorInt int progressColor) {
        this.mProgressColor = progressColor;
        updateProgressDrawable();
    }

    public void setProgressBackgroundColor(@ColorInt int backgroundColor) {
        this.mProgressBackgroundColor = backgroundColor;
        updateProgressDrawable();
    }

    public void setProgressCompat(int progress, boolean animate) {
        if (animate) {
            final ObjectAnimator animator = ObjectAnimator.ofInt(this, PROGRESS_PROPERTY, getProgress(), progress * PROGRESS_RANGE_MULTIPLIER);
            animator.setDuration(PROGRESS_ANIM_DURATION);
            animator.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
            animator.start();
        } else {
            setProgress(progress * PROGRESS_RANGE_MULTIPLIER);
        }

    }

    private void updateProgressDrawable() {
        LayerDrawable progressBarDrawable = (LayerDrawable) getProgressDrawable();
        Drawable backgroundDrawable = progressBarDrawable.findDrawableByLayerId(android.R.id.background);
        Drawable progressDrawable = progressBarDrawable.findDrawableByLayerId(android.R.id.progress);
        TintUtil.tintDrawable(backgroundDrawable, mProgressBackgroundColor);
        TintUtil.tintDrawable(progressDrawable, mProgressColor);
    }

    private static final Property<ProgressBar, Integer> PROGRESS_PROPERTY = new Property<ProgressBar, Integer>(Integer.class, "progress") {

        @Override
        public void set(ProgressBar object, Integer value) {
            object.setProgress(value);
        }

        @Override
        public Integer get(ProgressBar object) {
            return object.getProgress();
        }
    };

}
