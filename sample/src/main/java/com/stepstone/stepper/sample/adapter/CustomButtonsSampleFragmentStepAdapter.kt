package com.stepstone.stepper.sample.adapter

import android.content.Context
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager

import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.sample.R
import com.stepstone.stepper.sample.step.fragment.StepFragmentSample
import com.stepstone.stepper.viewmodel.StepViewModel

class CustomButtonsSampleFragmentStepAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {

    override fun createStep(position: Int): Step {
        when (position) {
            0 -> return StepFragmentSample.newInstance(R.layout.fragment_step)
            1 -> return StepFragmentSample.newInstance(R.layout.fragment_step2)
            2 -> return StepFragmentSample.newInstance(R.layout.fragment_step3)
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        val builder = StepViewModel.Builder(context)
                .setTitle(R.string.tab_title)
        when (position) {
            0 -> builder
                    .setNextButtonLabel("This way")
                    .setBackButtonLabel("Cancel")
                    .setNextButtonEndDrawableResId(R.drawable.ms_forward_arrow)
                    .setBackButtonStartDrawableResId(StepViewModel.NULL_DRAWABLE)
            1 -> builder
                    .setNextButtonLabel(R.string.go_to_summary)
                    .setBackButtonLabel("Go to first")
                    .setBackButtonStartDrawableResId(R.drawable.ms_back_arrow)
            2 -> builder.setBackButtonLabel("Go back")
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
        return builder.create()
    }
}