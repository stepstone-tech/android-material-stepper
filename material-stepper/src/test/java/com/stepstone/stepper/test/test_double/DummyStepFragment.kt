package com.stepstone.stepper

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A dummy fragment with no view.
 */
open class DummyStepFragment : Fragment(), Step {

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null
    }

    override fun onError(error: VerificationError) {}
}