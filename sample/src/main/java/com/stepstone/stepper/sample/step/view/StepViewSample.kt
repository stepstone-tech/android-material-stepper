package com.stepstone.stepper.sample.step.view

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.sample.OnNavigationBarListener
import com.stepstone.stepper.sample.R

/**
 * Created by leonardo on 18/12/16.
 */

class StepViewSample(context: Context) : FrameLayout(context), Step {

    companion object {

        private const val TAP_THRESHOLD = 2
    }

    private var i = 0

    private var onNavigationBarListener: OnNavigationBarListener? = null

    private var button: Button? = null

    init {
        init(context)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val c = context
        if (c is OnNavigationBarListener) {
            this.onNavigationBarListener = c
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.onNavigationBarListener = null
    }

    @Suppress("DEPRECATION")
    private fun init(context: Context) {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_step, this, true)
        button = v.findViewById(R.id.button)

        updateNavigationBar()

        button = v.findViewById(R.id.button)
        button?.text = Html.fromHtml("Taps: <b>$i</b>")
        button?.setOnClickListener {
            button?.text = Html.fromHtml("Taps: <b>${++i}</b>")
            updateNavigationBar()
        }
    }

    private val isAboveThreshold: Boolean
        get() = i >= TAP_THRESHOLD

    override fun verifyStep(): VerificationError? {
        return if (isAboveThreshold) null else VerificationError("Click ${TAP_THRESHOLD - i} more times!")
    }

    private fun updateNavigationBar() {
        onNavigationBarListener?.onChangeEndButtonsEnabled(isAboveThreshold)
    }

    override fun onSelected() {
        updateNavigationBar()
    }

    override fun onError(error: VerificationError) {
        button?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_error))
    }

}
