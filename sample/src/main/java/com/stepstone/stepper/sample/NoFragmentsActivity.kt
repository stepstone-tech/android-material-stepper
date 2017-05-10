package com.stepstone.stepper.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.sample.adapter.SampleStepAdapter

import butterknife.BindView
import butterknife.ButterKnife

class NoFragmentsActivity : AppCompatActivity(), StepperLayout.StepperListener, OnNavigationBarListener {

    companion object {

        private const val CURRENT_STEP_POSITION_KEY = "position"
    }

    @BindView(R.id.stepperLayout)
    lateinit var mStepperLayout: StepperLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_frag)
        ButterKnife.bind(this)
        val startingStepPosition = savedInstanceState?.getInt(CURRENT_STEP_POSITION_KEY) ?: 0
        val sampleStepAdapter = SampleStepAdapter(this)
        mStepperLayout.setAdapter(sampleStepAdapter, startingStepPosition)
        mStepperLayout.setListener(this)
    }

    override fun onBackPressed() {
        val currentStepPosition = mStepperLayout.currentStepPosition
        if (currentStepPosition > 0) {
            mStepperLayout.onBackClicked()
        } else {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.currentStepPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onCompleted(completeButton: View) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show()
    }

    override fun onError(verificationError: VerificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStepSelected(newStepPosition: Int) {
        Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show()
    }

    override fun onReturn() {
        finish()
    }

    override fun onChangeEndButtonsEnabled(enabled: Boolean) {
        mStepperLayout.setNextButtonVerificationFailed(!enabled)
        mStepperLayout.setCompleteButtonVerificationFailed(!enabled)
    }

}
