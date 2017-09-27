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

package com.stepstone.stepper.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.internal.feedback.StepperFeedbackType
import com.stepstone.stepper.sample.adapter.StepperFeedbackFragmentStepAdapter

import butterknife.BindView
import butterknife.ButterKnife

class StepperFeedbackActivity : AppCompatActivity() {

    companion object {

        private const val CURRENT_STEP_POSITION_KEY = "position"
    }

    @BindView(R.id.stepperLayout)
    lateinit var stepperLayout: StepperLayout

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Stepper sample"

        setContentView(R.layout.activity_stepper_feedback)
        ButterKnife.bind(this)
        val startingStepPosition = savedInstanceState?.getInt(CURRENT_STEP_POSITION_KEY) ?: 0
        stepperLayout.setAdapter(StepperFeedbackFragmentStepAdapter(supportFragmentManager, this), startingStepPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.currentStepPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val currentStepPosition = stepperLayout.currentStepPosition
        if (currentStepPosition > 0) {
            //do nothing when operation is in progress
            if (!stepperLayout.isInProgress) {
                stepperLayout.onBackClicked()
            }
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_stepper_feedback, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        if (!stepperLayout.isInProgress
                && (itemId == R.id.menu_feedback_content_progress
                || itemId == R.id.menu_feedback_content_fade
                || itemId == R.id.menu_feedback_content_overlay
                || itemId == R.id.menu_feedback_tabs
                || itemId == R.id.menu_feedback_nav
                || itemId == R.id.menu_feedback_content_interaction)) {
            toggleItem(item)
            val tabsEnabled = menu?.findItem(R.id.menu_feedback_tabs)?.isChecked
            val contentProgressEnabled = menu?.findItem(R.id.menu_feedback_content_progress)?.isChecked
            val contentFadeEnabled = menu?.findItem(R.id.menu_feedback_content_fade)?.isChecked
            val contentOverlayEnabled = menu?.findItem(R.id.menu_feedback_content_overlay)?.isChecked
            val disablingBottomNavigationEnabled = menu?.findItem(R.id.menu_feedback_nav)?.isChecked
            val disablingContentInteractionEnabled = menu?.findItem(R.id.menu_feedback_content_interaction)?.isChecked

            var feedbackMask = 0
            if (tabsEnabled == true) {
                feedbackMask = feedbackMask or StepperFeedbackType.TABS
            }
            if (contentProgressEnabled == true) {
                feedbackMask = feedbackMask or StepperFeedbackType.CONTENT_PROGRESS
            }
            if (contentFadeEnabled == true) {
                feedbackMask = feedbackMask or StepperFeedbackType.CONTENT_FADE
            }
            if (contentOverlayEnabled == true) {
                feedbackMask = feedbackMask or StepperFeedbackType.CONTENT_OVERLAY
            }
            if (disablingBottomNavigationEnabled == true) {
                feedbackMask = feedbackMask or StepperFeedbackType.DISABLED_BOTTOM_NAVIGATION
            }
            if (disablingContentInteractionEnabled == true) {
                feedbackMask = feedbackMask or StepperFeedbackType.DISABLED_CONTENT_INTERACTION
            }
            if (feedbackMask == 0) {
                feedbackMask = StepperFeedbackType.NONE
            }
            stepperLayout.setFeedbackType(feedbackMask)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun toggleItem(item: MenuItem) {
        item.isChecked = !item.isChecked
    }

}
