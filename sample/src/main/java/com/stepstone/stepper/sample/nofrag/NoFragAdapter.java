package com.stepstone.stepper.sample.nofrag;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractStepAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leonardo on 18/12/16.
 */

public class NoFragAdapter extends AbstractStepAdapter<NoFragView> {

    private List<NoFragView> pages = new LinkedList<>();
    private final Context context;

    public NoFragAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Step findStep(ViewPager viewPager, int position) {
        return pages.size() > 0 ? pages.get(position) : null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NoFragView view = createStep(position);
        pages.add(view);

        container.addView(view);

        return view;
    }

    @Override
    public NoFragView createStep(int position) {
        NoFragView view = new NoFragView(context);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
