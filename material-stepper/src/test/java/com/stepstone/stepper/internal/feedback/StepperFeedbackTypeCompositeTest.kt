package com.stepstone.stepper.internal.feedback

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.stepstone.stepper.test.assertion.StepperFeedbackTypeCompositeAssert.Companion.assertThat
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class StepperFeedbackTypeCompositeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
    }

    val mockChild: StepperFeedbackType = mock {}

    val mockSecondChild: StepperFeedbackType = mock {}

    val composite: StepperFeedbackTypeComposite = StepperFeedbackTypeComposite()

    @Test
    fun `Should have no child components by default`() {
        assertThat(composite)
                .hasNoChildComponents()
    }

    @Test
    fun `Should be able add child components`() {
        //when
        addChildren()

        //then
        assertThat(composite)
                .hasXChildComponents(2)
                .hasChildComponent(mockChild)
                .hasChildComponent(mockSecondChild)
    }

    @Test
    fun `Should propagate showing progress to child components if available`() {
        //given
        addChildren()

        //when
        composite.showProgress(PROGRESS_MESSAGE)

        //then
        verify(mockChild).showProgress(PROGRESS_MESSAGE)
        verify(mockSecondChild).showProgress(PROGRESS_MESSAGE)
    }

    @Test
    fun `Should not propagate showing progress to child components if no children added`() {
        composite.showProgress(PROGRESS_MESSAGE)
    }

    @Test
    fun `Should propagate hide progress to child components if available`() {
        //given
        addChildren()

        //when
        composite.hideProgress()

        //then
        verify(mockChild).hideProgress()
        verify(mockSecondChild).hideProgress()
    }

    @Test
    fun `Should not propagate hide progress to child components if no children added`() {
        composite.hideProgress()
    }

    private fun addChildren() {
        composite.addComponent(mockChild)
        composite.addComponent(mockSecondChild)
    }
}