package com.stepstone.stepper.test.assertion

import com.stepstone.stepper.viewmodel.StepViewModel
import org.assertj.core.api.AbstractAssert
import org.junit.Assert.assertEquals

/**
 * @author Piotr Zawadzki
 */
class StepViewModelAssert constructor(actual: StepViewModel) : AbstractAssert<StepViewModelAssert, StepViewModel>(actual, StepViewModelAssert::class.java) {

    companion object {

        fun assertThat(actual: StepViewModel): StepViewModelAssert {
            return StepViewModelAssert(actual)
        }

    }

    fun hasTitle(title: CharSequence?): StepViewModelAssert {
        assertEquals("Incorrect title!", title, actual.title)
        return this
    }

    fun hasSubtitle(subtitle: CharSequence?): StepViewModelAssert {
        assertEquals("Incorrect subtitle!", subtitle, actual.subtitle)
        return this
    }

    fun hasEndButtonLabel(endButtonLabel: CharSequence?): StepViewModelAssert {
        assertEquals("Incorrect label for the Complete/Next button!", endButtonLabel, actual.endButtonLabel)
        return this
    }

    fun hasBackButtonLabel(backButtonLabel: CharSequence?): StepViewModelAssert {
        assertEquals("Incorrect label for the Back button!", backButtonLabel, actual.backButtonLabel)
        return this
    }

    fun hasNextButtonEndDrawableResId(nextButtonEndDrawableResId: Int): StepViewModelAssert {
        assertEquals("Incorrect drawable resource id for the Next button!", nextButtonEndDrawableResId, actual.nextButtonEndDrawableResId)
        return this
    }

    fun hasBackButtonStartDrawableResId(backButtonStartDrawableResId: Int): StepViewModelAssert {
        assertEquals("Incorrect drawable resource id for the Back button!", backButtonStartDrawableResId, actual.backButtonStartDrawableResId)
        return this
    }

    fun hasEndButtonVisible(endButtonVisible: Boolean): StepViewModelAssert {
        assertEquals("Incorrect Next/Complete button visibility!", endButtonVisible, actual.isEndButtonVisible)
        return this
    }

    fun hasBackButtonVisible(backButtonVisible: Boolean): StepViewModelAssert {
        assertEquals("Incorrect Back button visibility!", backButtonVisible, actual.isBackButtonVisible)
        return this
    }

}
