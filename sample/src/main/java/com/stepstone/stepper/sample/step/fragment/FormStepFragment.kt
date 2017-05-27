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
import android.text.TextUtils
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import butterknife.BindView
import butterknife.OnEditorAction
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.sample.OnProceedListener
import com.stepstone.stepper.sample.R

internal class FormStepFragment : ButterKnifeFragment(), Step {

    companion object {

        fun newInstance(): FormStepFragment {
            return FormStepFragment()
        }
    }

    @BindView(R.id.editText)
    lateinit var editText: EditText

    private var onProceedListener: OnProceedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnProceedListener) {
            onProceedListener = context as OnProceedListener?
        } else {
            throw IllegalStateException("Activity must implement OnProceedListener")
        }
    }

    override val layoutResId: Int
        get() = R.layout.fragment_step_form

    override fun verifyStep(): VerificationError? {
        return if (TextUtils.isEmpty(editText.text.toString()))
            VerificationError("Password cannot be empty")
        else
            null
    }

    override fun onSelected() {}

    override fun onError(error: VerificationError) {
        editText.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake_error))
    }

    @OnEditorAction(R.id.editText)
    fun onEditorAction(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE && onProceedListener != null) {
            onProceedListener?.onProceed()
        }
        return true
    }
}
