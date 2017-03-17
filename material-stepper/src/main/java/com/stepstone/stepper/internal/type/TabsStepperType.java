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

package com.stepstone.stepper.internal.type;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.util.SparseBooleanArray;
import android.view.View;

import com.stepstone.stepper.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.adapter.StepAdapter;
import com.stepstone.stepper.internal.widget.TabsContainer;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Stepper type which displays horizontal stepper with tabs.
 */
@RestrictTo(LIBRARY)
public class TabsStepperType extends AbstractStepperType {

    private static final List<CharSequence> EDIT_MODE_STEP_TITLES = Arrays.<CharSequence>asList("Step 1", "Step 2");

    private final TabsContainer mTabsContainer;

    public TabsStepperType(StepperLayout stepperLayout) {
        super(stepperLayout);
        mTabsContainer = (TabsContainer) stepperLayout.findViewById(R.id.ms_stepTabsContainer);
        mTabsContainer.setVisibility(View.VISIBLE);
        mTabsContainer.setSelectedColor(stepperLayout.getSelectedColor());
        mTabsContainer.setUnselectedColor(stepperLayout.getUnselectedColor());
        mTabsContainer.setErrorColor(stepperLayout.getErrorColor());
        mTabsContainer.setDividerWidth(stepperLayout.getTabStepDividerWidth());
        mTabsContainer.setListener(stepperLayout);

        if (stepperLayout.isInEditMode()) {
            mTabsContainer.setSteps(EDIT_MODE_STEP_TITLES);
            mTabsContainer.updateSteps(0, new SparseBooleanArray());
            mTabsContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStepSelected(int newStepPosition, boolean userTriggeredChange) {
        if (!mStepperLayout.isShowErrorStateEnabled()) {
            mStepErrors.clear();
        }
        mTabsContainer.updateSteps(newStepPosition, mStepErrors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewAdapter(@NonNull StepAdapter stepAdapter) {
        super.onNewAdapter(stepAdapter);
        List<CharSequence> titles = new ArrayList<>();
        final int stepCount = stepAdapter.getCount();
        for (int i = 0; i < stepCount; i++) {
            final StepViewModel stepViewModel = stepAdapter.getViewModel(i);
            titles.add(stepViewModel.getTitle());
        }
        mTabsContainer.setSteps(titles);
        mTabsContainer.setVisibility(stepCount > 1 ? View.VISIBLE : View.GONE);
    }
}
