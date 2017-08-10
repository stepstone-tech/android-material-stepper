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
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.List;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Layout used for displaying tabs from the horizontal stepper.
 * Steps must be added via {@link #setSteps(List)}.<br>
 * <b>NOTE:</b> {@link #setSelectedColor(int)} and {@link #setUnselectedColor(int)} should be set before calling {@link #setSteps(List)}.
 */
@RestrictTo(LIBRARY)
public class TabsContainer extends FrameLayout {

    /**
     * Listeners for actions on individual tabs of the horizontal stepper
     */
    public interface TabItemListener {

        /**
         * Called when a tab gets clicked
         *
         * @param position position of the tab/step
         */
        @UiThread
        void onTabClicked(int position);

        TabItemListener NULL = new TabItemListener() {
            @Override
            public void onTabClicked(int position) {
            }
        };
    }

    @ColorInt
    private int mUnselectedColor;

    @ColorInt
    private int mSelectedColor;

    @ColorInt
    private int mErrorColor;

    private int mDividerWidth = StepperLayout.DEFAULT_TAB_DIVIDER_WIDTH;

    private int mContainerLateralPadding;

    private HorizontalScrollView mTabsScrollView;

    private LinearLayout mTabsInnerContainer;

    private TabItemListener mListener = TabItemListener.NULL;

    private List<StepViewModel> mStepViewModels;

    public TabsContainer(Context context) {
        this(context, null);
    }

    public TabsContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabsContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.ms_tabs_container, this, true);

        mSelectedColor = ContextCompat.getColor(context, R.color.ms_selectedColor);
        mUnselectedColor = ContextCompat.getColor(context, R.color.ms_unselectedColor);
        mErrorColor = ContextCompat.getColor(context, R.color.ms_errorColor);
        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.TabsContainer, defStyleAttr, 0);

            if (a.hasValue(R.styleable.TabsContainer_ms_activeTabColor)) {
                mSelectedColor = a.getColor(R.styleable.TabsContainer_ms_activeTabColor, mSelectedColor);
            }
            if (a.hasValue(R.styleable.TabsContainer_ms_inactiveTabColor)) {
                mUnselectedColor = a.getColor(R.styleable.TabsContainer_ms_inactiveTabColor, mUnselectedColor);
            }

            a.recycle();
        }
        mContainerLateralPadding = context.getResources().getDimensionPixelOffset(R.dimen.ms_tabs_container_lateral_padding);

        mTabsInnerContainer = (LinearLayout) findViewById(R.id.ms_stepTabsInnerContainer);
        mTabsScrollView = (HorizontalScrollView) findViewById(R.id.ms_stepTabsScrollView);
    }

    public void setUnselectedColor(@ColorInt int unselectedColor) {
        this.mUnselectedColor = unselectedColor;
    }

    public void setSelectedColor(@ColorInt int selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    public void setErrorColor(@ColorInt int mErrorColor) {
        this.mErrorColor = mErrorColor;
    }

    public void setDividerWidth(int dividerWidth) {
        this.mDividerWidth = dividerWidth;
    }

    public void setListener(@NonNull TabItemListener listener) {
        this.mListener = listener;
    }

    /**
     * Sets the steps to display in the {@link TabsContainer}.
     *
     * @param stepViewModels a list of step info holders
     */
    public void setSteps(List<StepViewModel> stepViewModels) {
        this.mStepViewModels = stepViewModels;

        mTabsInnerContainer.removeAllViews();
        for (int i = 0; i < stepViewModels.size(); i++) {
            final View tab = createStepTab(i, stepViewModels.get(i));
            mTabsInnerContainer.addView(tab, tab.getLayoutParams());
        }
    }

    /**
     * Changes the position of the current step and updates the UI based on it.
     * @param currentStepPosition new current step
     * @param stepErrors map containing error state for step positions
     * @param showErrorMessageEnabled true if an error message below step title should appear when an error occurs
     */
    public void updateSteps(int currentStepPosition, SparseArray<VerificationError> stepErrors, boolean showErrorMessageEnabled) {
        int size = mStepViewModels.size();
        for (int i = 0; i < size; i++) {
            StepTab childTab = (StepTab) mTabsInnerContainer.getChildAt(i);
            boolean done = i < currentStepPosition;
            final boolean current = i == currentStepPosition;

            VerificationError error = stepErrors.get(i);
            childTab.updateState(error, done, current, showErrorMessageEnabled);
            if (current) {
                mTabsScrollView.smoothScrollTo(childTab.getLeft() - mContainerLateralPadding, 0);
            }
        }
    }

    private View createStepTab(final int position, @NonNull StepViewModel stepViewModel) {
        StepTab view = (StepTab) LayoutInflater.from(getContext()).inflate(R.layout.ms_step_tab_container, mTabsInnerContainer, false);
        view.setStepNumber(String.valueOf(position + 1));
        view.toggleDividerVisibility(!isLastPosition(position));
        view.setStepTitle(stepViewModel.getTitle());
        view.setStepSubtitle(stepViewModel.getSubtitle());
        view.setSelectedColor(mSelectedColor);
        view.setUnselectedColor(mUnselectedColor);
        view.setErrorColor(mErrorColor);
        view.setDividerWidth(mDividerWidth);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTabClicked(position);
            }
        });

        return view;
    }

    private boolean isLastPosition(int position) {
        return position == mStepViewModels.size() - 1;
    }
}
