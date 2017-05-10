package com.stepstone.stepper.sample

import android.os.Bundle

/**
 * Created by leonardo on 10/01/17.
 */

class ShowErrorOnBackTabActivity : AbstractStepperActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_default_tabs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStepperLayout.isShowErrorStateEnabled = true
        mStepperLayout.isShowErrorStateOnBackEnabled = true
    }

}