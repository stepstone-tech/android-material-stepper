package com.stepstone.stepper.test.assertion

import android.support.annotation.IdRes
import com.stepstone.stepper.R
import com.stepstone.stepper.StepperLayout
import org.assertj.android.api.Assertions
import org.assertj.android.api.view.ViewAssert
import org.assertj.android.api.widget.AbstractLinearLayoutAssert

/**
 * @author Piotr Zawadzki
 */
class StepperLayoutAssert constructor(actual: StepperLayout) : AbstractLinearLayoutAssert<StepperLayoutAssert, StepperLayout>(actual, StepperLayoutAssert::class.java) {

    companion object {

        fun assertThat(actual: StepperLayout): StepperLayoutAssert {
            return StepperLayoutAssert(actual)
        }

    }

    fun hasHorizontalProgressBarShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepProgressBar).isVisible
        return this
    }

    fun hasHorizontalProgressBarHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepProgressBar).isGone
        return this
    }

    fun hasDottedProgressBarShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepDottedProgressBar).isVisible
        return this
    }

    fun hasDottedProgressBarHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepDottedProgressBar).isGone
        return this
    }

    fun hasTabsShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepTabsContainer).isVisible
        return this
    }

    fun hasTabsHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_stepTabsContainer).isGone
        return this
    }

    fun hasBottomNavigationShown(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_bottomNavigation).isVisible
        return this
    }

    fun hasBottomNavigationHidden(): StepperLayoutAssert {
        hasNotNullChildView(R.id.ms_bottomNavigation).isGone
        return this
    }

    private fun hasNotNullChildView(@IdRes childId: Int): ViewAssert {
        val progressBar = actual.findViewById(childId)
        return Assertions.assertThat(progressBar).isNotNull
    }
}
