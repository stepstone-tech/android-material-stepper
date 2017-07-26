package com.stepstone.stepper.test.assertion

import com.stepstone.stepper.internal.feedback.StepperFeedbackType
import com.stepstone.stepper.internal.feedback.StepperFeedbackTypeComposite
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions

/**
 *
 * @author Piotr Zawadzki
 */
class StepperFeedbackTypeCompositeAssert constructor(actual: StepperFeedbackTypeComposite) : AbstractAssert<StepperFeedbackTypeCompositeAssert, StepperFeedbackTypeComposite>(actual, StepperFeedbackTypeCompositeAssert::class.java) {

    companion object {

        fun assertThat(actual: StepperFeedbackTypeComposite): StepperFeedbackTypeCompositeAssert {
            return StepperFeedbackTypeCompositeAssert(actual)
        }

    }

    fun hasChildComponentOf(childClass: Class<out StepperFeedbackType>): StepperFeedbackTypeCompositeAssert {
        Assertions.assertThat(actual.childComponents)
                .isNotNull
                .isNotEmpty
                .hasAtLeastOneElementOfType(childClass)
        return this
    }

    fun hasNoChildComponents(): StepperFeedbackTypeCompositeAssert {
        Assertions.assertThat(actual.childComponents)
                .isNotNull
                .isEmpty()
        return this
    }

    fun hasXChildComponents(childCount: Int): StepperFeedbackTypeCompositeAssert {
        Assertions.assertThat(actual.childComponents)
                .isNotNull
                .hasSize(childCount)
        return this
    }

    fun hasChildComponent(child: StepperFeedbackType): StepperFeedbackTypeCompositeAssert {
        Assertions.assertThat(actual.childComponents)
                .isNotNull
                .isNotEmpty
                .contains(child)
        return this
    }
}