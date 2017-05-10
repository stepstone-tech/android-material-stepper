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

import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.sample.adapter.PassDataBetweenStepsFragmentStepAdapter

import butterknife.BindView
import butterknife.ButterKnife

class PassDataBetweenStepsActivity : AppCompatActivity(), DataManager {


    companion object {

        private const val CURRENT_STEP_POSITION_KEY = "position"

        private const val DATA = "data"
    }

    @BindView(R.id.stepperLayout)
    lateinit var mStepperLayout: StepperLayout

    private var mData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Stepper sample"

        setContentView(R.layout.activity_pass_data_between_steps)
        ButterKnife.bind(this)
        val startingStepPosition = savedInstanceState?.getInt(CURRENT_STEP_POSITION_KEY) ?: 0
        mData = savedInstanceState?.getString(DATA)
        mStepperLayout.setAdapter(PassDataBetweenStepsFragmentStepAdapter(supportFragmentManager, this), startingStepPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.currentStepPosition)
        outState.putString(DATA, mData)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val currentStepPosition = mStepperLayout.currentStepPosition
        if (currentStepPosition > 0) {
            mStepperLayout.onBackClicked()
        } else {
            finish()
        }
    }

    override fun saveData(data: String?) {
        mData = data
    }

    override fun getData(): String? {
        return mData
    }
}
