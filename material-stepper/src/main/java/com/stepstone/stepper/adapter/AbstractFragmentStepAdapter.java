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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.Step;

/**
 * A base adapter class which returns step fragments to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 */
public abstract class AbstractFragmentStepAdapter
        extends FragmentPagerAdapter
        implements StepAdapter {

    private final FragmentManager mFragmentManager;

    public AbstractFragmentStepAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public final Fragment getItem(int position) {
        return (Fragment) createStep(position);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Step findStep(ViewPager viewPager, int position) {
        String fragmentTag =  "android:switcher:" + viewPager.getId() + ":" + this.getItemId(position);
        return (Step) mFragmentManager.findFragmentByTag(fragmentTag);
    }

    /** {@inheritDoc} */
    @StringRes
    public int getNextButtonText(int position) {
        return DEFAULT_NEXT_BUTTON_TEXT;
    }

    /** {@inheritDoc} */
    @Override
    public PagerAdapter getPagerAdapter() {
        return this;
    }
}
