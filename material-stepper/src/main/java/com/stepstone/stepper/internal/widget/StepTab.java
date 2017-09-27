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

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.internal.util.ObjectsCompat;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * A widget for a single tab in the {@link TabsContainer}.
 */
@RestrictTo(LIBRARY)
public class StepTab extends RelativeLayout {

    private static final float ALPHA_TRANSPARENT = 0.0f;

    private static final float ALPHA_INACTIVE_STEP_TITLE = 0.54f;

    private static final float ALPHA_ACTIVE_STEP_TITLE = 0.87f;

    private static final float ALPHA_OPAQUE = 1.0f;

    private static final float HALF_SIZE_SCALE = 0.5f;

    private static final float FULL_SIZE_SCALE = 1.0f;

    @VisibleForTesting
    final TextView mStepNumberTextView;

    @VisibleForTesting
    final View mStepDivider;

    @VisibleForTesting
    final TextView mStepTitleTextView;

    @VisibleForTesting
    final TextView mStepSubtitleTextView;

    @VisibleForTesting
    final ImageView mStepDoneIndicator;

    @VisibleForTesting
    final ImageView mStepIconBackground;

    @VisibleForTesting
    CharSequence mSubtitle;

    /**
     * Current UI state of the tab. See {@link AbstractState} for more details.
     */
    @VisibleForTesting
    AbstractState mCurrentState = new InactiveNumberState();

    @ColorInt
    private int mUnselectedColor;

    @ColorInt
    private int mSelectedColor;

    @ColorInt
    private int mErrorColor;

    @ColorInt
    private int mTitleColor;

    @ColorInt
    private int mSubtitleColor;

    private Typeface mNormalTypeface;

    private Typeface mBoldTypeface;

    private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();

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
        mErrorColor = ContextCompat.getColor(context, R.color.ms_errorColor);

        mStepNumberTextView = (TextView) findViewById(R.id.ms_stepNumber);
        mStepDoneIndicator = (ImageView) findViewById(R.id.ms_stepDoneIndicator);
        mStepIconBackground = (ImageView) findViewById(R.id.ms_stepIconBackground);
        mStepDivider = findViewById(R.id.ms_stepDivider);
        mStepTitleTextView = (TextView) findViewById(R.id.ms_stepTitle);
        mStepSubtitleTextView = (TextView) findViewById(R.id.ms_stepSubtitle);

        mTitleColor = mStepTitleTextView.getCurrentTextColor();
        mSubtitleColor = mStepSubtitleTextView.getCurrentTextColor();

        Typeface typeface = mStepTitleTextView.getTypeface();
        mNormalTypeface = Typeface.create(typeface, Typeface.NORMAL);
        mBoldTypeface = Typeface.create(typeface, Typeface.BOLD);
        Drawable avd = createCircleToWarningDrawable();
        mStepIconBackground.setImageDrawable(avd);
    }

    /**
     * Changes the visibility of the horizontal line in the tab
     *
     * @param show true if the line should be shown, false otherwise
     */
    public void toggleDividerVisibility(boolean show) {
        mStepDivider.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Updates the UI state of the tab and sets {@link #mCurrentState} based on the arguments.
     *  @param error   not null if an error/warning should be shown, null if not an error
     * @param done    true the step was completed, if warning is not shown and this is <code>true</code> a done indicator will be shown
     * @param current true if this is the currently selected step
     * @param showErrorMessageEnabled true if an error message below step title should appear when an error occurs
     */
    public void updateState(@Nullable final VerificationError error, final boolean done, final boolean current, boolean showErrorMessageEnabled) {
        // FIXME: 05/03/2017 stop tabs from changing positions due to changing font type (does not happen e.g. on API 16, investigate further)
        mStepTitleTextView.setTypeface(current ? mBoldTypeface : mNormalTypeface);

        if (error != null) {
            mCurrentState.changeToWarning(showErrorMessageEnabled ? error.getErrorMessage() : null);
        } else if (done) {
            mCurrentState.changeToDone();
        } else if (current) {
            mCurrentState.changeToActiveNumber();
        } else {
            mCurrentState.changeToInactiveNumber();
        }
    }

    /**
     * Sets the name of the step
     *
     * @param title step name
     */
    public void setStepTitle(CharSequence title) {
        mStepTitleTextView.setText(title);
    }

    /**
     * Sets the optional step description. This will be displayed below step title unless it's empty or an error message is set.
     *
     * @param subtitle optional step description
     */
    public void setStepSubtitle(CharSequence subtitle) {
        this.mSubtitle = subtitle;
        updateSubtitle(subtitle);
    }

    /**
     * Sets the position of the step
     *
     * @param number step position
     */
    public void setStepNumber(CharSequence number) {
        mStepNumberTextView.setText(number);
    }

    public void setUnselectedColor(int unselectedColor) {
        this.mUnselectedColor = unselectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    public void setErrorColor(int errorColor) {
        this.mErrorColor = errorColor;
    }

    public void setDividerWidth(int dividerWidth) {
        mStepDivider.getLayoutParams().width = dividerWidth != StepperLayout.DEFAULT_TAB_DIVIDER_WIDTH
                ? dividerWidth
                : getResources().getDimensionPixelOffset(R.dimen.ms_step_tab_divider_length);
    }

    private Drawable createCircleToWarningDrawable() {
        return createAnimatedVectorDrawable(R.drawable.ms_animated_vector_circle_to_warning_24dp);
    }

    private Drawable createWarningToCircleDrawable() {
        return createAnimatedVectorDrawable(R.drawable.ms_animated_vector_warning_to_circle_24dp);
    }

    /**
     * Inflates an animated vector drawable.
     *
     * @param animatedVectorDrawableResId resource ID for the animated vector
     * @return animated vector drawable
     */
    public Drawable createAnimatedVectorDrawable(@DrawableRes int animatedVectorDrawableResId) {
        return AnimatedVectorDrawableCompat.create(getContext(), animatedVectorDrawableResId);
    }

    private void updateSubtitle(@Nullable CharSequence newSubtitle) {
        if (ObjectsCompat.equals(newSubtitle, mStepSubtitleTextView.getText())) {
            return;
        }

        //Do not hide the subtitle if error message is empty
        if (!TextUtils.isEmpty(mSubtitle) && TextUtils.isEmpty(newSubtitle)) {
            newSubtitle = mSubtitle;
        }

        mStepSubtitleTextView.setText(newSubtitle);
        mStepSubtitleTextView.setVisibility(!TextUtils.isEmpty(newSubtitle) ? VISIBLE : GONE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(this);
        }
    }

    /**
     * <p>Base state class of the tab.
     * It is used to specify what should happen from the UI perspective
     * when transitioning to other states, e.g. which views to hide or tint.</p>
     * <p>Subclasses include:</p>
     * <ul>
     * <li>{@link InactiveNumberState} - for when we show the step number, but this step still hasn't been reached</li>
     * <li>{@link ActiveNumberState} - for when we show the step number of the currently active tab</li>
     * <li>{@link DoneState} - for when the step has already been completed and the user has moved to the next step</li>
     * <li>{@link WarningState} - for when there has been an error on this step (if {@link StepperLayout#setShowErrorStateEnabled(boolean)}
     * or <code>ms_showErrorStateEnabled</code> was set to <i>true</i>)</li>
     * </ul>
     */
    @VisibleForTesting
    abstract class AbstractState {

        @CallSuper
        protected void changeToInactiveNumber() {
            updateSubtitle(mSubtitle);
            StepTab.this.mCurrentState = new InactiveNumberState();
        }

        @CallSuper
        protected void changeToActiveNumber() {
            updateSubtitle(mSubtitle);
            StepTab.this.mCurrentState = new ActiveNumberState();
        }

        @CallSuper
        protected void changeToDone() {
            updateSubtitle(mSubtitle);
            StepTab.this.mCurrentState = new DoneState();
        }

        @CallSuper
        protected void changeToWarning(@Nullable CharSequence errorMessage) {
            mStepDoneIndicator.setVisibility(View.GONE);
            mStepNumberTextView.setVisibility(View.GONE);
            mStepIconBackground.setColorFilter(mErrorColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setTextColor(mErrorColor);
            mStepSubtitleTextView.setTextColor(mErrorColor);
            updateSubtitle(errorMessage);
            StepTab.this.mCurrentState = new WarningState();
        }
    }

    @VisibleForTesting
    abstract class AbstractNumberState extends AbstractState {

        @Override
        @CallSuper
        protected void changeToWarning(@Nullable CharSequence errorMessage) {
            Drawable avd = createCircleToWarningDrawable();
            mStepIconBackground.setImageDrawable(avd);
            ((Animatable) avd).start();
            super.changeToWarning(errorMessage);
        }

        @Override
        @CallSuper
        protected void changeToDone() {
            mStepDoneIndicator.setVisibility(VISIBLE);
            mStepNumberTextView.setVisibility(GONE);
            super.changeToDone();
        }

    }

    @VisibleForTesting
    class InactiveNumberState extends AbstractNumberState {

        @Override
        protected void changeToInactiveNumber() {
            mStepIconBackground.setColorFilter(mUnselectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setTextColor(mTitleColor);
            mStepTitleTextView.setAlpha(ALPHA_INACTIVE_STEP_TITLE);
            mStepSubtitleTextView.setTextColor(mSubtitleColor);
            super.changeToInactiveNumber();
        }

        @Override
        protected void changeToActiveNumber() {
            mStepIconBackground.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setAlpha(ALPHA_ACTIVE_STEP_TITLE);
            super.changeToActiveNumber();
        }

        @Override
        protected void changeToDone() {
            mStepIconBackground.setColorFilter(mSelectedColor);
            mStepTitleTextView.setAlpha(ALPHA_ACTIVE_STEP_TITLE);
            super.changeToDone();
        }
    }

    @VisibleForTesting
    class ActiveNumberState extends AbstractNumberState {

        @Override
        protected void changeToInactiveNumber() {
            mStepIconBackground.setColorFilter(mUnselectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setAlpha(ALPHA_INACTIVE_STEP_TITLE);
            super.changeToInactiveNumber();
        }
    }

    @VisibleForTesting
    class DoneState extends AbstractState {

        @Override
        protected void changeToInactiveNumber() {
            mStepDoneIndicator.setVisibility(GONE);
            mStepNumberTextView.setVisibility(VISIBLE);
            mStepIconBackground.setColorFilter(mUnselectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setAlpha(ALPHA_INACTIVE_STEP_TITLE);
            super.changeToInactiveNumber();
        }

        @Override
        protected void changeToActiveNumber() {
            mStepDoneIndicator.setVisibility(GONE);
            mStepNumberTextView.setVisibility(VISIBLE);
            super.changeToActiveNumber();
        }

        @Override
        protected void changeToWarning(@Nullable CharSequence errorMessage) {
            Drawable avd = createCircleToWarningDrawable();
            mStepIconBackground.setImageDrawable(avd);
            ((Animatable) avd).start();
            super.changeToWarning(errorMessage);
        }
    }

    @VisibleForTesting
    class WarningState extends AbstractState {

        @Override
        protected void changeToDone() {
            animateViewIn(mStepDoneIndicator);

            mStepIconBackground.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setTextColor(mTitleColor);
            mStepSubtitleTextView.setTextColor(mSubtitleColor);
            super.changeToDone();
        }

        @Override
        protected void changeToInactiveNumber() {
            animateViewIn(mStepNumberTextView);

            mStepIconBackground.setColorFilter(mUnselectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setTextColor(mTitleColor);
            mStepTitleTextView.setAlpha(ALPHA_INACTIVE_STEP_TITLE);
            mStepSubtitleTextView.setTextColor(mSubtitleColor);

            super.changeToInactiveNumber();
        }

        @Override
        protected void changeToActiveNumber() {
            animateViewIn(mStepNumberTextView);

            mStepIconBackground.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_IN);
            mStepTitleTextView.setTextColor(mTitleColor);
            mStepSubtitleTextView.setTextColor(mSubtitleColor);
            super.changeToActiveNumber();
        }

        private void animateViewIn(final View view) {
            Drawable avd = createWarningToCircleDrawable();
            mStepIconBackground.setImageDrawable(avd);
            ((Animatable) avd).start();

            view.setVisibility(View.VISIBLE);
            view.setAlpha(ALPHA_TRANSPARENT);
            view.setScaleX(HALF_SIZE_SCALE);
            view.setScaleY(HALF_SIZE_SCALE);
            view.animate()
                    .setInterpolator(mAccelerateInterpolator)
                    .alpha(ALPHA_OPAQUE)
                    .scaleX(FULL_SIZE_SCALE)
                    .scaleY(FULL_SIZE_SCALE);

        }
    }

}
