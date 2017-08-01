package com.stepstone.stepper.internal.feedback

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.stepstone.stepper.R
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.internal.widget.StepViewPager
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class DisabledContentInteractionStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
    }

    val mockStepPager: StepViewPager = mock { }

    val mockStepperLayout: StepperLayout = mock {
        on { findViewById(R.id.ms_stepPager) } doReturn mockStepPager
    }

    val feedbackType: DisabledContentInteractionStepperFeedbackType = DisabledContentInteractionStepperFeedbackType(mockStepperLayout)

    @Test
    fun `Should block content interaction when showing progress`() {
        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        verify(mockStepPager).setBlockTouchEventsFromChildrenEnabled(true)
    }

    @Test
    fun `Should enable content interaction when hiding progress`() {
        //when
        feedbackType.hideProgress()

        //then
        verify(mockStepPager).setBlockTouchEventsFromChildrenEnabled(false)
    }
}