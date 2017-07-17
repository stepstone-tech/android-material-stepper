package com.stepstone.stepper

import android.os.Bundle
import com.stepstone.stepper.test.test_double.SpyStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

/**
 * A [StepperLayoutActivity] which also sets an adapter in [onCreate].
 */
open class StepperLayoutWithAdapterActivity : StepperLayoutActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModels = listOf(getFirstStepViewModel(), getSecondStepViewModel(), getLastStepViewModel())

        val stepAdapter = SpyStepAdapter(supportFragmentManager, this, viewModels)
        stepperLayout.adapter = stepAdapter
    }

    open fun getFirstStepViewModel(): StepViewModel {
        return defaultStepViewModel()
    }

    open fun getSecondStepViewModel(): StepViewModel {
        return defaultStepViewModel()
    }

    open fun getLastStepViewModel(): StepViewModel {
        return defaultStepViewModel()
    }

    private fun defaultStepViewModel(): StepViewModel {
        return StepViewModel.Builder(this)
                .setTitle("Dummy title")
                .create()
    }
}