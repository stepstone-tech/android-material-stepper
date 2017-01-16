package com.stepstone.stepper.sample.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractStepAdapter;
import com.stepstone.stepper.sample.step.view.StepViewSample;

/**
 * A naive implementation of {@link AbstractStepAdapter}.
 * This does not keep the view data on rotation, etc.
 * It also keeps a reference to the created views.
 */
public class SampleStepAdapter extends AbstractStepAdapter {

    private final SparseArray<Step> pages = new SparseArray<>();

    public SampleStepAdapter(Context context) {
        super(context);
    }

    @Override
    public StepViewSample createStep(int position) {
        return new StepViewSample(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Step findStep(ViewPager viewPager, int position) {
        return pages.size() > 0 ? pages.get(position) : null;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        Step step = pages.get(position);
        if (step == null) {
            step = createStep(position);
            pages.put(position, step);
        }

        View stepView = (View) step;
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
}
