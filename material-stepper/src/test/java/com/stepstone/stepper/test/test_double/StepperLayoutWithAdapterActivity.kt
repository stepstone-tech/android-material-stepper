package com.stepstone.stepper

import android.os.Bundle
import com.stepstone.stepper.test.test_double.SpyStepAdapter

/**
 * A [StepperLayoutActivity] which also sets an adapter in [onCreate].
 */
class StepperLayoutWithAdapterActivity : StepperLayoutActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stepAdapter = SpyStepAdapter(supportFragmentManager, this)
        stepperLayout.adapter = stepAdapter
    }
}