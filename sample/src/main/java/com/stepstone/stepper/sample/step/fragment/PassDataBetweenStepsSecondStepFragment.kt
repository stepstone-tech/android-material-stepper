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

package com.stepstone.stepper.sample.step.fragment

import android.content.Context
import android.widget.TextView

import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.sample.DataManager
import com.stepstone.stepper.sample.R

import butterknife.BindView

internal class PassDataBetweenStepsSecondStepFragment : ButterKnifeFragment(), Step {

    companion object {

        fun newInstance(): PassDataBetweenStepsSecondStepFragment {
            return PassDataBetweenStepsSecondStepFragment()
        }
    }

    @BindView(R.id.stepContent)
    lateinit var stepContent: TextView

    lateinit var dataManager: DataManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DataManager) {
            dataManager = context
        } else {
            throw IllegalStateException("Activity must implement DataManager interface!")
        }
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {
        stepContent.text = "Entered text: ${dataManager.getData()}\n"
    }

    override fun onError(error: VerificationError) {}

    override val layoutResId: Int
        get() = R.layout.fragment_with_text_content

}
