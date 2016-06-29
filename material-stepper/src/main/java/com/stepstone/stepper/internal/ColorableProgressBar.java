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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.stepstone.stepper.R;
import com.stepstone.stepper.util.TintUtil;

/**
 * A {@link ProgressBar} which exposes methods for coloring primary progress and progress background colors individually.
 */
public class ColorableProgressBar extends ProgressBar {

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

        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.ColorableProgressBar, defStyleAttr, 0);

            if (a.hasValue(R.styleable.ColorableProgressBar_ms_progressPrimaryColor)) {
                mProgressColor = a.getColor(R.styleable.ColorableProgressBar_ms_progressPrimaryColor, mProgressColor);
            }
            if (a.hasValue(R.styleable.ColorableProgressBar_ms_progressBackgroundColor)) {
                mProgressBackgroundColor = a.getColor(R.styleable.ColorableProgressBar_ms_progressBackgroundColor, mProgressBackgroundColor);
            }

            a.recycle();
        }
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

    public void setProgressColor(@ColorInt int progressColor) {
        this.mProgressColor = progressColor;
        updateProgressDrawable();
    }

    public void setProgressBackgroundColor(@ColorInt int backgroundColor) {
        this.mProgressBackgroundColor = backgroundColor;
        updateProgressDrawable();
    }

    private void updateProgressDrawable() {
        LayerDrawable progressBarDrawable = (LayerDrawable) getProgressDrawable();
        Drawable backgroundDrawable = progressBarDrawable.findDrawableByLayerId(android.R.id.background);
        Drawable progressDrawable = progressBarDrawable.findDrawableByLayerId(android.R.id.progress);
        TintUtil.tintDrawable(backgroundDrawable, mProgressBackgroundColor);
        TintUtil.tintDrawable(progressDrawable, mProgressColor);
    }
}
