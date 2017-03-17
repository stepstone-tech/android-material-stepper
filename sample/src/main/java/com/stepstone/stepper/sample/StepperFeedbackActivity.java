/*
Copyright 2017 StepStone Services

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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.internal.feedback.StepperFeedbackType;
import com.stepstone.stepper.sample.adapter.StepperFeedbackFragmentStepAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StepperFeedbackActivity extends AppCompatActivity {

    private static final String CURRENT_STEP_POSITION_KEY = "position";

    @Bind(R.id.stepperLayout)
    StepperLayout mStepperLayout;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Stepper sample");

        setContentView(R.layout.activity_stepper_feedback);
        ButterKnife.bind(this);
        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        mStepperLayout.setAdapter(new StepperFeedbackFragmentStepAdapter(getSupportFragmentManager(), this), startingStepPosition);
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
            //do nothing when operation is in progress
            if (!mStepperLayout.isInProgress()) {
                mStepperLayout.onBackClicked();
            }
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stepper_feedback, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (!mStepperLayout.isInProgress()
                && (itemId == R.id.menu_feedback_content || itemId == R.id.menu_feedback_tabs || itemId == R.id.menu_feedback_nav)) {
            toggleItem(item);
            boolean tabsEnabled = mMenu.findItem(R.id.menu_feedback_tabs).isChecked();
            boolean contentEnabled = mMenu.findItem(R.id.menu_feedback_content).isChecked();
            boolean disablingBottomNavigationEnabled = mMenu.findItem(R.id.menu_feedback_nav).isChecked();

            int feedbackMask = 0;
            if (tabsEnabled) {
                feedbackMask |= StepperFeedbackType.TABS;
            }
            if (contentEnabled) {
                feedbackMask |= StepperFeedbackType.CONTENT;
            }
            if (disablingBottomNavigationEnabled) {
                feedbackMask |= StepperFeedbackType.DISABLED_BOTTOM_NAVIGATION;
            }
            if (feedbackMask == 0) {
                feedbackMask = StepperFeedbackType.NONE;
            }
            mStepperLayout.setFeedbackType(feedbackMask);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleItem(MenuItem item) {
        item.setChecked(!item.isChecked());
    }


}
