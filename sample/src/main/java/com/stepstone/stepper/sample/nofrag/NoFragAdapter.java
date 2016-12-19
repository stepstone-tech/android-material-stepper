package com.stepstone.stepper.sample.nofrag;

import android.content.Context;

import com.stepstone.stepper.adapter.AbstractStepAdapter;

/**
 * Created by leonardo on 18/12/16.
 */

public class NoFragAdapter extends AbstractStepAdapter {

    private final Context context;

    public NoFragAdapter(Context context) {
        this.context = context;
    }

    @Override
    public NoFragView createStep(int position) {
        return new NoFragView(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
