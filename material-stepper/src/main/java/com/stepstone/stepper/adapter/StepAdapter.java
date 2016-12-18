package com.stepstone.stepper.adapter;

import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.Step;

/**
 * Created by leonardo on 18/12/16.
 */

public interface StepAdapter<T> {
    T createStep(int position);
    Step findStep(ViewPager viewPager, int position);
    @StringRes int getNextButtonText(int position);
    int getCount();
    PagerAdapter getAdapter();
}
