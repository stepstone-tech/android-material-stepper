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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.util.TintUtil;

/**
 * A widget for a single tab in the {@link TabsContainer}.
 */
public class StepTab extends RelativeLayout {

    private static final float INACTIVE_STEP_TITLE_ALPHA = 0.54f;

    private static final int OPAQUE_ALPHA = 1;

    @ColorInt
    private int mUnselectedColor;

    @ColorInt
    private int mSelectedColor;

    private final TextView mStepNumber;

    private final View mStepDivider;

    private final TextView mStepTitle;

    private final ImageView mStepDoneIndicator;

    private int mDividerWidth = StepperLayout.DEFAULT_TAB_DIVIDER_WIDTH;

    public StepTab(Context context) {
        this(context, null);
    }

    public StepTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.ms_step_tab, this, true);

        mSelectedColor = ContextCompat.getColor(context, R.color.ms_selectedColor);
        mUnselectedColor = ContextCompat.getColor(context, R.color.ms_unselectedColor);

        mStepNumber = (TextView) findViewById(R.id.ms_stepNumber);
        mStepDoneIndicator = (ImageView) findViewById(R.id.ms_stepDoneIndicator);
        mStepDivider = findViewById(R.id.ms_stepDivider);
        mStepTitle = ((TextView) findViewById(R.id.ms_stepTitle));
    }

    /**
     * Changes the visibility of the horizontal line in the tab
     * @param show true if the line should be shown, false otherwise
     */
    public void toggleDividerVisibility(boolean show) {
        mStepDivider.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Updates tab's UI
     * @param done true if the step is done and the step's number should be replaced with a <i>done</i> icon, false otherwise
     * @param current true if the step is the current step, false otherwise
     */
    public void updateState(final boolean done, final boolean current) {
        mStepDoneIndicator.setVisibility(done ? View.VISIBLE : View.GONE);
        mStepNumber.setVisibility(!done ? View.VISIBLE : View.GONE);
        colorViewBackground(done ? mStepDoneIndicator : mStepNumber, done || current);

        mStepTitle.setTypeface(current ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        mStepTitle.setAlpha(done || current ? OPAQUE_ALPHA : INACTIVE_STEP_TITLE_ALPHA);
    }

    /**
     * Sets the name of the step
     * @param title step name
     */
    public void setStepTitle(CharSequence title) {
        mStepTitle.setText(title);
    }

    /**
     * Sets the position of the step
     * @param number step position
     */
    public void setStepNumber(CharSequence number) {
        mStepNumber.setText(number);
    }

    public void setUnselectedColor(int unselectedColor) {
        this.mUnselectedColor = unselectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    private void colorViewBackground(View view, boolean selected) {
        Drawable d = view.getBackground();
        TintUtil.tintDrawable(d, selected ? mSelectedColor : mUnselectedColor);
    }

    public void setDividerWidth(int dividerWidth) {
        this.mDividerWidth = dividerWidth;
        mStepDivider.getLayoutParams().width = mDividerWidth != StepperLayout.DEFAULT_TAB_DIVIDER_WIDTH
                ? dividerWidth
                : getResources().getDimensionPixelOffset(R.dimen.ms_step_tab_divider_length);
    }

}
