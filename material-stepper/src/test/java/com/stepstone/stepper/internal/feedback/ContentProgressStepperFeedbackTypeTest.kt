package com.stepstone.stepper.internal.feedback

import android.view.View
import android.widget.ProgressBar
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.stepstone.stepper.R
import com.stepstone.stepper.StepperLayout
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class ContentProgressStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
    }

    val mockProgressBar: ProgressBar = mock {  }

    val mockStepperLayout: StepperLayout = mock {
        on { findViewById(R.id.ms_stepPagerProgressBar) } doReturn mockProgressBar
    }

    val feedbackType: ContentProgressStepperFeedbackType = ContentProgressStepperFeedbackType(mockStepperLayout)

    @Test
    fun `Should show progress bar when showing progress`() {
        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        verify(mockProgressBar).visibility = View.VISIBLE
    }

    @Test
    fun `Should hide progress bar when hiding progress`() {
        //when
        feedbackType.hideProgress()

        //then
        verify(mockProgressBar).visibility = View.GONE
    }

}