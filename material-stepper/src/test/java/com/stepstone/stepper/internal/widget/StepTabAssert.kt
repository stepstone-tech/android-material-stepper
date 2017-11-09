package com.stepstone.stepper.internal.widget

import android.widget.TextView
import com.stepstone.stepper.R
import org.assertj.android.api.widget.AbstractRelativeLayoutAssert

/**
 * @author Piotr Zawadzki
 */
class StepTabAssert constructor(actual: StepTab) : AbstractRelativeLayoutAssert<StepTabAssert, StepTab>(actual, StepTabAssert::class.java) {

    companion object {

        fun assertThat(actual: StepTab): StepTabAssert {
            return StepTabAssert(actual)
        }

    }

    fun <T : StepTab.AbstractState> isInState(stateClass: Class<T>): StepTabAssert {
        org.assertj.core.api.Assertions.assertThat(actual.mCurrentState).isInstanceOf(stateClass)
        return this
    }

    fun hasTitle(title: CharSequence): StepTabAssert {
        org.assertj.android.api.Assertions.assertThat(getTitleView())
                .isNotNull
                .isVisible
                .hasText(title)
        return this
    }

    fun hasSubtitle(subtitle: CharSequence): StepTabAssert {
        org.assertj.android.api.Assertions.assertThat(getSubtitleView())
                .isNotNull
                .isVisible
                .hasText(subtitle)
        return this
    }

    fun isSubtitleHidden(): StepTabAssert {
        org.assertj.android.api.Assertions.assertThat(getSubtitleView())
                .isNotNull
                .isGone
        return this
    }

    private fun getTitleView(): TextView {
        return actual.findViewById(R.id.ms_stepTitle)
    }

    private fun getSubtitleView(): TextView {
        return actual.findViewById(R.id.ms_stepSubtitle)
    }

}
