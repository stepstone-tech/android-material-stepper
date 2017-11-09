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

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.text.Html
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import butterknife.BindView
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.sample.OnNavigationBarListener
import com.stepstone.stepper.sample.R

internal class StepFragmentSample : ButterKnifeFragment(), Step {

    companion object {

        private const val CLICKS_KEY = "clicks"

        private const val TAP_THRESHOLD = 2

        private const val LAYOUT_RESOURCE_ID_ARG_KEY = "messageResourceId"

        fun newInstance(@LayoutRes layoutResId: Int): StepFragmentSample {
            val args = Bundle()
            args.putInt(LAYOUT_RESOURCE_ID_ARG_KEY, layoutResId)
            val fragment = StepFragmentSample()
            fragment.arguments = args
            return fragment
        }
    }

    @BindView(R.id.button)
    lateinit var button: Button

    private var i = 0

    private var onNavigationBarListener: OnNavigationBarListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnNavigationBarListener) {
            onNavigationBarListener = context
        }
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            i = savedInstanceState.getInt(CLICKS_KEY)
        }

        updateNavigationBar()

        button.text = Html.fromHtml("Taps: <b>$i</b>")
        button.setOnClickListener {
            button.text = Html.fromHtml("Taps: <b>${++i}</b>")
            updateNavigationBar()
        }
    }

    override val layoutResId: Int
        get() = arguments!!.getInt(LAYOUT_RESOURCE_ID_ARG_KEY)

    override fun verifyStep(): VerificationError? {
        return if (isAboveThreshold) null else VerificationError("Click ${TAP_THRESHOLD - i} more times!")
    }

    private val isAboveThreshold: Boolean
        get() = i >= TAP_THRESHOLD

    override fun onSelected() {
        updateNavigationBar()
    }

    override fun onError(error: VerificationError) {
        button.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake_error))
    }

    private fun updateNavigationBar() {
        onNavigationBarListener?.onChangeEndButtonsEnabled(isAboveThreshold)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CLICKS_KEY, i)
        super.onSaveInstanceState(outState)
    }

}
