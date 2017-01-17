package com.stepstone.stepper.sample;

import android.os.Bundle;

/**
 * Created by leonardo on 10/01/17.
 */

public class ShowErrorOnBackTabActivity extends AbstractStepperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStepperLayout.setShowErrorState(true);
        mStepperLayout.setShowErrorStateOnBack(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_default_tabs;
    }
}