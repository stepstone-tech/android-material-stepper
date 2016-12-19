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

package com.stepstone.stepper.sample;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.sample.step.StepFragmentSample;

public class DifferentNextButtonStepperActivity extends AppCompatActivity {

    private static final String CURRENT_STEP_POSITION_KEY = "position";

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Stepper sample");

        setContentView(R.layout.activity_default_dots);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        mStepperLayout.setAdapter(new MyStepperFragmentAdapter(getSupportFragmentManager()), startingStepPosition);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.getCurrentStepPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        final int currentStepPosition = mStepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            mStepperLayout.setCurrentStepPosition(currentStepPosition - 1);
        } else {
            finish();
        }
    }

    private static class MyStepperFragmentAdapter extends AbstractFragmentStepAdapter {

        MyStepperFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Step createStep(int position) {
            switch (position) {
                case 0:
                    return StepFragmentSample.newInstance(R.layout.fragment_step);
                case 1:
                    return StepFragmentSample.newInstance(R.layout.fragment_step2);
                case 2:
                    return StepFragmentSample.newInstance(R.layout.fragment_step3);
                default:
                    throw new IllegalArgumentException("Unsupported position: " + position);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @StringRes
        @Override
        public int getNextButtonText(int position) {
            switch (position) {
                case 0:
                    return R.string.ms_next;
                case 1:
                    return R.string.go_to_summary;
                default:
                    throw new IllegalArgumentException("Unsupported position: " + position);
            }
        }

    }
}
