package com.stepstone.stepper.test.test_double

import android.content.Context
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager
import android.util.SparseArray
import com.nhaarman.mockito_kotlin.spy
import com.stepstone.stepper.DummyStepFragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

/**
 * Creates Spy [DummyStepFragment]s which can be later verified.
 */
class SpyStepAdapter(fm: FragmentManager, context: Context, val viewModels: List<StepViewModel>) : AbstractFragmentStepAdapter(fm, context) {

    val steps = SparseArray<Step>()

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        return viewModels[position]
    }

    override fun createStep(position: Int): Step {
        val stepFragment = spy(DummyStepFragment())
        steps.put(position, stepFragment)
        return stepFragment
    }

    override fun getCount() = viewModels.size
}