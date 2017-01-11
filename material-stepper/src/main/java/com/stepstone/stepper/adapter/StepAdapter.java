package com.stepstone.stepper.adapter;

import android.support.annotation.StringRes;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.Step;

/**
 * Interface to be used as model to {@link com.stepstone.stepper.StepperLayout}.
 */
public interface StepAdapter {

    int DEFAULT_NEXT_BUTTON_TEXT = -1;

    /**
     * Create each step of the {@link com.stepstone.stepper.StepperLayout}.
     * @param position The position of the {@link PagerAdapter} to be used inside the {@link ViewPager}.
     * @return the step to be used inside the {@link com.stepstone.stepper.StepperLayout}.
     */
    Step createStep(int position);

    /**
     * Finds the given step without creating it.
     * @see FragmentPagerAdapter#makeFragmentName(int, long)
     * @param viewPager view pager to use for displaying step fragments
     * @param position step position
     * @return step fragment
     */
    Step findStep(ViewPager viewPager, int position);

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
    @StringRes int getNextButtonText(int position);

    /**
     * Get the step count.
     * @return the quantity of steps
     */
    int getCount();

    /**
     * Method for internal purpose. Should not be inherited.
     * @return the adapter to be used in the {@link ViewPager}.
     */
    PagerAdapter getPagerAdapter();
}
