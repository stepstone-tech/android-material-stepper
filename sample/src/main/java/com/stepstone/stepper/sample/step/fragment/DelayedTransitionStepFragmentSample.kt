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

package com.stepstone.stepper.sample.step.fragment

import android.os.Handler
import android.support.annotation.UiThread
import android.support.v7.app.AlertDialog
import android.support.v7.widget.SwitchCompat
import android.widget.Toast
import butterknife.BindView
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.sample.R

internal class DelayedTransitionStepFragmentSample : ButterKnifeFragment(), BlockingStep {

    companion object {

        fun newInstance(): DelayedTransitionStepFragmentSample {
            return DelayedTransitionStepFragmentSample()
        }
    }

    @BindView(R.id.operationSwitch)
    lateinit var operationSwitch: SwitchCompat

    private var dialog: AlertDialog? = null

    override val layoutResId: Int
        get() = R.layout.fragment_step_delayed_transition

    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {}

    override fun onError(error: VerificationError) {}

    @UiThread
    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setView(R.layout.dialog_loader)
        builder.setCancelable(false)
        dialog = builder.show()
        Handler().postDelayed({
            dialog?.dismiss()
            if (shouldOperationSucceed()) {
                callback.goToNextStep()
            } else {
                val activity = this@DelayedTransitionStepFragmentSample.activity
                if (activity != null && isResumed) {
                    Toast.makeText(activity, "Operation failed!", Toast.LENGTH_SHORT).show()
                }
                callback.stepperLayout.updateErrorState(VerificationError("Operation failed!"))
            }
        }, 2000L)
    }

    /**
     * Notifies this step that the complete button/tab was clicked, the step was verified
     * and the user can complete the flow. This is so that the current step might perform
     * some last minute operations e.g. a network call before completing the flow.
     * [StepperLayout.OnCompleteClickedCallback] must be called once these operations finish.

     * @param callback callback to call once the user wishes to complete the flow
     */
    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback) {
        callback.complete()
    }

    private fun shouldOperationSucceed(): Boolean {
        return operationSwitch.isChecked
    }

    @UiThread
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback) {
        Toast.makeText(this.context, "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show()
        callback.goToPrevStep()
    }
}
