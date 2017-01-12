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

package com.stepstone.stepper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stepstone.stepper.adapter.StepAdapter;
import com.stepstone.stepper.internal.ColorableProgressBar;
import com.stepstone.stepper.internal.DottedProgressBar;
import com.stepstone.stepper.internal.RightNavigationButton;
import com.stepstone.stepper.internal.TabsContainer;
import com.stepstone.stepper.type.AbstractStepperType;
import com.stepstone.stepper.type.StepperTypeFactory;
import com.stepstone.stepper.util.AnimationUtil;
import com.stepstone.stepper.util.TintUtil;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Stepper widget implemented according to the <a href="https://www.google.com/design/spec/components/steppers.html">Material documentation</a>.<br>
 * It allows for setting three types of steppers:<br>
 * - mobile dots stepper,<br>
 * - mobile progress bar stepper,<br>
 * - horizontal stepper with tabs.<br>
 * Include this stepper in the layout XML file and choose a stepper type with <code>ms_stepperType</code>.<br>
 * Check out <code>values/attrs.xml - StepperLayout</code> for a complete list of customisable properties.
 */
public class StepperLayout extends LinearLayout implements TabsContainer.TabItemListener {

    public static final int DEFAULT_TAB_DIVIDER_WIDTH = -1;

    /**
     * A listener for events of {@link StepperLayout}.
     */
    public interface StepperListener {

        /**
         * Called when all of the steps were completed successfully
         *
         * @param completeButton the complete button that was clicked to complete the flow
         */
        void onCompleted(View completeButton);

        /**
         * Called when a verification error occurs for one of the steps
         *
         * @param verificationError verification error
         */
        void onError(VerificationError verificationError);

        /**
         * Called when the current step position changes
         *
         * @param newStepPosition new step position
         */
        void onStepSelected(int newStepPosition);

        /**
         * Called when the Previous step button was pressed while on the first step
         * (the button is not present by default on first step).
         */
        void onReturn();

        StepperListener NULL = new StepperListener() {
            @Override
            public void onCompleted(View completeButton) {
            }

            @Override
            public void onError(VerificationError verificationError) {
            }

            @Override
            public void onStepSelected(int newStepPosition) {
            }

            @Override
            public void onReturn() {
            }
        };
    }

    public final class OnNextClickedCallback {

        @UiThread
        public final void goToNextStep() {
            final int totalStepCount = mStepAdapter.getCount();

            if (mCurrentStepPosition >= totalStepCount - 1) {
                return;
            }

            mCurrentStepPosition++;
            onUpdate(mCurrentStepPosition, true);
        }

    }

    public final class OnBackClickedCallback {

        @UiThread
        public final void goToPrevStep() {
            if (mCurrentStepPosition <= 0) {
                if (mShowBackButtonOnFirstStep) {
                    mListener.onReturn();
                }
                return;
            }
            mCurrentStepPosition--;
            onUpdate(mCurrentStepPosition, true);
        }

    }

    private ViewPager mPager;

    private Button mBackNavigationButton;

    private RightNavigationButton mNextNavigationButton;

    private RightNavigationButton mCompleteNavigationButton;

    private ViewGroup mStepNavigation;

    private DottedProgressBar mDottedProgressBar;

    private ColorableProgressBar mProgressBar;

    private TabsContainer mTabsContainer;

    private ColorStateList mBackButtonColor;

    private ColorStateList mNextButtonColor;

    private ColorStateList mCompleteButtonColor;

    @ColorInt
    private int mUnselectedColor;

    @ColorInt
    private int mSelectedColor;

    @DrawableRes
    private int mBottomNavigationBackground;

    @DrawableRes
    private int mBackButtonBackground;

    @DrawableRes
    private int mNextButtonBackground;

    @DrawableRes
    private int mCompleteButtonBackground;

    private int mTabStepDividerWidth = DEFAULT_TAB_DIVIDER_WIDTH;

    private String mBackButtonText;

    private String mNextButtonText;

    private String mCompleteButtonText;

    private boolean mShowBackButtonOnFirstStep;

    private int mTypeIdentifier = AbstractStepperType.PROGRESS_BAR;

    private StepAdapter mStepAdapter;

    private AbstractStepperType mStepperType;

    private int mCurrentStepPosition;

    @NonNull
    private StepperListener mListener = StepperListener.NULL;

    private OnClickListener mOnBackClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            onPrevious();
        }
    };

    private OnClickListener mOnNextClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            onNext();
        }
    };

    private OnClickListener mOnCompleteClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            onComplete(v);
        }
    };

    public StepperLayout(Context context) {
        this(context, null);
    }

    public StepperLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ms_stepperStyle);
    }

    public StepperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public void setListener(@NonNull StepperListener listener) {
        this.mListener = listener;
    }

    /**
     * Sets the new step adapter and updates the stepper layout based on the new adapter.
     *
     * @param stepAdapter step adapter
     */
    public void setAdapter(@NonNull StepAdapter stepAdapter) {
        this.mStepAdapter = stepAdapter;
        mPager.setAdapter(stepAdapter.getPagerAdapter());

        mStepperType.onNewAdapter(stepAdapter);

        // this is so that the fragments in the adapter can be created BEFORE the onUpdate() method call
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onUpdate(mCurrentStepPosition, false);
            }
        });

    }

    /**
     * Sets the new step adapter and updates the stepper layout based on the new adapter.
     *
     * @param stepAdapter         step adapter
     * @param currentStepPosition the initial step position, must be in the range of the adapter item count
     */
    public void setAdapter(@NonNull StepAdapter stepAdapter, @IntRange(from = 0) int currentStepPosition) {
        this.mCurrentStepPosition = currentStepPosition;
        setAdapter(stepAdapter);
    }

    /**
     * Overrides the default page transformer used in the underlying {@link ViewPager}
     *
     * @param pageTransformer new page transformer
     */
    public void setPageTransformer(@Nullable ViewPager.PageTransformer pageTransformer) {
        mPager.setPageTransformer(false, pageTransformer);
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public int getUnselectedColor() {
        return mUnselectedColor;
    }

    public int getTabStepDividerWidth() {
        return mTabStepDividerWidth;
    }

    @Override
    @UiThread
    public void onTabClicked(int position) {
        if (position > mCurrentStepPosition) {
            onNext();
        } else if (position < mCurrentStepPosition) {
            setCurrentStepPosition(position);
        }
    }

    public void setCurrentStepPosition(int currentStepPosition) {
        this.mCurrentStepPosition = currentStepPosition;
        onUpdate(currentStepPosition, true);
    }

    public int getCurrentStepPosition() {
        return mCurrentStepPosition;
    }

    public void setNextButtonVerificationFailed(boolean verificationFailed) {
        mNextNavigationButton.setVerificationFailed(verificationFailed);
    }

    public void setCompleteButtonVerificationFailed(boolean verificationFailed) {
        mCompleteNavigationButton.setVerificationFailed(verificationFailed);
    }

    /**
     * Set the number of steps that should be retained to either side of the
     * current step in the view hierarchy in an idle state. Steps beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many steps will be kept offscreen in an idle state.
     * @see ViewPager#setOffscreenPageLimit(int)
     */
    public void setOffscreenPageLimit(int limit) {
        mPager.setOffscreenPageLimit(limit);
    }

    private void init(AttributeSet attrs, @AttrRes int defStyleAttr) {
        initDefaultValues();
        extractValuesFromAttributes(attrs, defStyleAttr);

        final Context context = getContext();
        LayoutInflater.from(context).inflate(R.layout.ms_stepper_layout, this, true);

        bindViews();

        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        initNavigation();

        mDottedProgressBar.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
        mTabsContainer.setVisibility(GONE);

        mStepperType = StepperTypeFactory.createType(mTypeIdentifier, this);
    }

    private void initNavigation() {
        mStepNavigation.setBackgroundResource(mBottomNavigationBackground);

        mBackNavigationButton.setText(mBackButtonText);
        mNextNavigationButton.setText(mNextButtonText);
        mCompleteNavigationButton.setText(mCompleteButtonText);

        //FIXME: 16/03/16 this is a workaround for tinting TextView's compound drawables on API 16-17 - when set in XML only the default color is used...
        Drawable chevronEndDrawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ic_chevron_end, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mNextNavigationButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, chevronEndDrawable, null);
        } else {
            mNextNavigationButton.setCompoundDrawablesWithIntrinsicBounds(null, null, chevronEndDrawable, null);
        }

        TintUtil.tintTextView(mBackNavigationButton, mBackButtonColor);
        TintUtil.tintTextView(mNextNavigationButton, mNextButtonColor);
        TintUtil.tintTextView(mCompleteNavigationButton, mCompleteButtonColor);

        setBackgroundIfPresent(mBackButtonBackground, mBackNavigationButton);
        setBackgroundIfPresent(mNextButtonBackground, mNextNavigationButton);
        setBackgroundIfPresent(mCompleteButtonBackground, mCompleteNavigationButton);

        mBackNavigationButton.setOnClickListener(mOnBackClickListener);
        mNextNavigationButton.setOnClickListener(mOnNextClickListener);
        mCompleteNavigationButton.setOnClickListener(mOnCompleteClickListener);
    }

    private void setBackgroundIfPresent(@DrawableRes int backgroundRes, View view) {
        if (backgroundRes != 0) {
            view.setBackgroundResource(backgroundRes);
        }
    }

    private void bindViews() {
        mPager = (ViewPager) findViewById(R.id.ms_stepPager);

        mBackNavigationButton = (Button) findViewById(R.id.ms_stepPrevButton);
        mNextNavigationButton = (RightNavigationButton) findViewById(R.id.ms_stepNextButton);
        mCompleteNavigationButton = (RightNavigationButton) findViewById(R.id.ms_stepCompleteButton);

        mStepNavigation = (ViewGroup) findViewById(R.id.ms_stepNavigation);

        mDottedProgressBar = (DottedProgressBar) findViewById(R.id.ms_stepDottedProgressBar);

        mProgressBar = (ColorableProgressBar) findViewById(R.id.ms_stepProgressBar);

        mTabsContainer = (TabsContainer) findViewById(R.id.ms_stepTabsContainer);
    }

    private void extractValuesFromAttributes(AttributeSet attrs, @AttrRes int defStyleAttr) {
        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.StepperLayout, defStyleAttr, 0);

            if (a.hasValue(R.styleable.StepperLayout_ms_backButtonColor)) {
                mBackButtonColor = a.getColorStateList(R.styleable.StepperLayout_ms_backButtonColor);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_nextButtonColor)) {
                mNextButtonColor = a.getColorStateList(R.styleable.StepperLayout_ms_nextButtonColor);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_completeButtonColor)) {
                mCompleteButtonColor = a.getColorStateList(R.styleable.StepperLayout_ms_completeButtonColor);
            }

            if (a.hasValue(R.styleable.StepperLayout_ms_activeStepColor)) {
                mSelectedColor = a.getColor(R.styleable.StepperLayout_ms_activeStepColor, mSelectedColor);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_inactiveStepColor)) {
                mUnselectedColor = a.getColor(R.styleable.StepperLayout_ms_inactiveStepColor, mUnselectedColor);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_bottomNavigationBackground)) {
                mBottomNavigationBackground = a.getResourceId(R.styleable.StepperLayout_ms_bottomNavigationBackground, mBottomNavigationBackground);
            }

            if (a.hasValue(R.styleable.StepperLayout_ms_backButtonBackground)) {
                mBackButtonBackground = a.getResourceId(R.styleable.StepperLayout_ms_backButtonBackground, 0);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_nextButtonBackground)) {
                mNextButtonBackground = a.getResourceId(R.styleable.StepperLayout_ms_nextButtonBackground, 0);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_completeButtonBackground)) {
                mCompleteButtonBackground = a.getResourceId(R.styleable.StepperLayout_ms_completeButtonBackground, 0);
            }

            if (a.hasValue(R.styleable.StepperLayout_ms_backButtonText)) {
                mBackButtonText = a.getString(R.styleable.StepperLayout_ms_backButtonText);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_nextButtonText)) {
                mNextButtonText = a.getString(R.styleable.StepperLayout_ms_nextButtonText);
            }
            if (a.hasValue(R.styleable.StepperLayout_ms_completeButtonText)) {
                mCompleteButtonText = a.getString(R.styleable.StepperLayout_ms_completeButtonText);
            }

            if (a.hasValue(R.styleable.StepperLayout_ms_tabStepDividerWidth)) {
                mTabStepDividerWidth = a.getDimensionPixelOffset(R.styleable.StepperLayout_ms_tabStepDividerWidth, -1);
            }

            mShowBackButtonOnFirstStep = a.getBoolean(R.styleable.StepperLayout_ms_showBackButtonOnFirstStep, false);

            if (a.hasValue(R.styleable.StepperLayout_ms_stepperType)) {
                mTypeIdentifier = a.getInt(R.styleable.StepperLayout_ms_stepperType, DEFAULT_TAB_DIVIDER_WIDTH);
            }

            a.recycle();
        }
    }

    private void initDefaultValues() {
        mBackButtonColor = mNextButtonColor = mCompleteButtonColor =
                ContextCompat.getColorStateList(getContext(), R.color.ms_bottomNavigationButtonTextColor);
        mSelectedColor = ContextCompat.getColor(getContext(), R.color.ms_selectedColor);
        mUnselectedColor = ContextCompat.getColor(getContext(), R.color.ms_unselectedColor);
        mBottomNavigationBackground = R.color.ms_bottomNavigationBackgroundColor;
        mBackButtonText = getContext().getString(R.string.ms_back);
        mNextButtonText = getContext().getString(R.string.ms_next);
        mCompleteButtonText = getContext().getString(R.string.ms_complete);
    }

    private boolean isLastPosition(int position) {
        return position == mStepAdapter.getCount() - 1;
    }

    private Step findCurrentStep() {
        return mStepAdapter.findStep(mPager, mCurrentStepPosition);
    }

    private void onPrevious() {
        Step step = findCurrentStep();

        OnBackClickedCallback onBackClickedCallback = new OnBackClickedCallback();
        if (step instanceof BlockingStep) {
            ((BlockingStep) step).onBackClicked(onBackClickedCallback);
        } else {
            onBackClickedCallback.goToPrevStep();
        }
    }

    @UiThread
    private void onNext() {
        Step step = findCurrentStep();

        if (verifyCurrentStep(step)) {
            return;
        }
        OnNextClickedCallback onNextClickedCallback = new OnNextClickedCallback();
        if (step instanceof BlockingStep) {
            ((BlockingStep) step).onNextClicked(onNextClickedCallback);
        } else {
            onNextClickedCallback.goToNextStep();
        }
    }

    private boolean verifyCurrentStep(Step step) {
        final VerificationError verificationError = step.verifyStep();
        if (verificationError != null) {
            onError(verificationError);
            return true;
        }
        return false;
    }

    private void onError(@NonNull VerificationError verificationError) {
        Step step = findCurrentStep();
        if (step != null) {
            step.onError(verificationError);
        }
        mListener.onError(verificationError);
    }

    private void onComplete(View completeButton) {
        Step step = findCurrentStep();
        if (verifyCurrentStep(step)) {
            return;
        }
        mListener.onCompleted(completeButton);
    }

    private void onUpdate(int newStepPosition, boolean animate) {
        mPager.setCurrentItem(newStepPosition);
        final boolean isLast = isLastPosition(newStepPosition);
        final boolean isFirst = newStepPosition == 0;
        AnimationUtil.fadeViewVisibility(mNextNavigationButton, isLast ? View.GONE : View.VISIBLE, animate);
        AnimationUtil.fadeViewVisibility(mCompleteNavigationButton, !isLast ? View.GONE : View.VISIBLE, animate);
        AnimationUtil.fadeViewVisibility(mBackNavigationButton, isFirst && !mShowBackButtonOnFirstStep ? View.GONE : View.VISIBLE, animate);

        final StepViewModel viewModel = mStepAdapter.getViewModel(newStepPosition);

        updateBackButtonText(viewModel);

        if (!isLast) {
            updateNextButtonText(viewModel);
        }

        mStepperType.onStepSelected(newStepPosition);
        mListener.onStepSelected(newStepPosition);
        Step step = mStepAdapter.findStep(mPager, newStepPosition);
        if (step != null) {
            step.onSelected();
        }
    }

    private void updateNextButtonText(@NonNull StepViewModel viewModel) {
        CharSequence nextButtonTextForStep = viewModel.getNextButtonLabel();
        if (nextButtonTextForStep == null) {
            mNextNavigationButton.setText(mNextButtonText);
        } else {
            mNextNavigationButton.setText(nextButtonTextForStep);
        }
    }

    private void updateBackButtonText(@NonNull StepViewModel viewModel) {
        CharSequence backButtonTextForStep = viewModel.getBackButtonLabel();
        if (backButtonTextForStep == null) {
            mBackNavigationButton.setText(mBackButtonText);
        } else {
            mBackNavigationButton.setText(backButtonTextForStep);
        }
    }
}
