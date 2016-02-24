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

package com.stepstone.stepper.adapter;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.Step;

/**
 * A base adapter class which returns step fragments to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 */
public abstract class AbstractStepAdapter<T extends Fragment & Step> extends FragmentPagerAdapter {

    public static final int DEFAULT_NEXT_BUTTON_TEXT = -1;

    private final FragmentManager mFragmentManager;

    public AbstractStepAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public final T getItem(int position) {
        return createStep(position);
    }

    protected abstract T createStep(int position);

    /**
     * Finds the given step without creating it.
     * @see FragmentPagerAdapter#makeFragmentName(int, long)
     * @param viewPager view pager to use for displaying step fragments
     * @param position step position
     * @return step fragment
     */
    public Step findStep(ViewPager viewPager, int position) {
        String fragmentTag =  "android:switcher:" + viewPager.getId() + ":" + this.getItemId(position);
        return (Step) mFragmentManager.findFragmentByTag(fragmentTag);
    }

    /**
     * Allows to override the text on the Next button per step.
     * For a given step position you need to return a String resource ID for the label to be used.
     * If you wish to change the text for selected steps only (and leave the default for the rest)
     * then return {@link #DEFAULT_NEXT_BUTTON_TEXT} for the default ones.
     * By default this method returns {@link #DEFAULT_NEXT_BUTTON_TEXT} for all steps.
     * This method is not invoked for the last step.
     * @param position step position
     * @return a String resource ID to override the default button text or {@link #DEFAULT_NEXT_BUTTON_TEXT} if the default text should be kept
     */
    @StringRes
    public int getNextButtonText(int position) {
        return DEFAULT_NEXT_BUTTON_TEXT;
    }

}
