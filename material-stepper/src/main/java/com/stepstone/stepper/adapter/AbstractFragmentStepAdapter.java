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

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * A base adapter class which returns step fragments to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 */
public abstract class AbstractFragmentStepAdapter
        extends FragmentPagerAdapter
        implements StepAdapter {

    @NonNull
    private final FragmentManager mFragmentManager;

    @NonNull
    protected final Context context;

    public AbstractFragmentStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm);
        this.mFragmentManager = fm;
        this.context = context;
    }

    @Override
    public final Fragment getItem(@IntRange(from = 0) int position) {
        return (Fragment) createStep(position);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Step findStep(ViewPager viewPager, @IntRange(from = 0) int position) {
        String fragmentTag =  "android:switcher:" + viewPager.getId() + ":" + this.getItemId(position);
        return (Step) mFragmentManager.findFragmentByTag(fragmentTag);
    }

    /** {@inheritDoc} */
    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        return new StepViewModel.Builder(context).create();
    }

    /** {@inheritDoc} */
    @Override
    public final PagerAdapter getPagerAdapter() {
        return this;
    }
}
