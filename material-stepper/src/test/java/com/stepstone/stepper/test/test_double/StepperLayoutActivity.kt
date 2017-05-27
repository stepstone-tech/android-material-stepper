package com.stepstone.stepper

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * An Activity with a StepperLayout added in [onCreate].
 */
open class StepperLayoutActivity : FragmentActivity() {

    internal lateinit var stepperLayoutAttributeSet: AttributeSet

    internal lateinit var stepperLayout: StepperLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        stepperLayout = StepperLayout(this, stepperLayoutAttributeSet)
        stepperLayout.layoutParams = layoutParams
        setContentView(stepperLayout)
    }

    internal fun withStepperLayoutAttributes(attributeSet: AttributeSet): StepperLayoutActivity {
        this.stepperLayoutAttributeSet = attributeSet
        return this
    }
}