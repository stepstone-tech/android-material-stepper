package com.stepstone.stepper.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.Step;

import java.util.LinkedList;
import java.util.List;

/**
 * A base adapter class which returns step to use inside of the {@link com.stepstone.stepper.StepperLayout}.
 * This class is intended to be inherited if you need to use {@link com.stepstone.stepper.StepperLayout} without fragments.
 * Otherwise, you should use {@link AbstractFragmentStepAdapter}
 */
public abstract class AbstractStepAdapter extends PagerAdapter implements StepAdapter<View> {

    private List<Step<View>> pages = new LinkedList<>();

    @Override
    public Step<View> findStep(ViewPager viewPager, int position) {
        return pages.size() > 0 ? pages.get(position) : null;
    }

    @Override
    public final View instantiateItem(ViewGroup container, int position) {
        Step<View> step = createStep(position);
        pages.add(step);

        View stepView = step.getStepView();
        container.addView(stepView);

        return stepView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /** {@inheritDoc} */
    @Override
    public int getNextButtonText(int position) {
        return DEFAULT_NEXT_BUTTON_TEXT;
    }

    /** {@inheritDoc} */
    @Override
    public PagerAdapter getPagerAdapter() {
        return this;
    }
}
