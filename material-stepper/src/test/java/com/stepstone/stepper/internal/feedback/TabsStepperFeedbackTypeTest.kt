package com.stepstone.stepper.internal.feedback

import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.test.TYPE_TABS
import com.stepstone.stepper.test.assertion.StepperLayoutAssert
import com.stepstone.stepper.test.createAttributeSetWithStepperType
import com.stepstone.stepper.test.createStepperLayoutInActivity
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class TabsStepperFeedbackTypeTest {

    companion object {
        val PROGRESS_MESSAGE = "loading..."
    }

    lateinit var stepperLayout: StepperLayout

    lateinit var feedbackType: TabsStepperFeedbackType

    @Before
    fun setUp() {
        stepperLayout = createStepperLayoutInActivity(createAttributeSetWithStepperType(TYPE_TABS))
        feedbackType = TabsStepperFeedbackType(stepperLayout)
    }

    @Test
    fun `Should show loading message in place of tabs and disable tab navigation when showing progress`() {
        //when
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //then
        assertStepperLayout()
                .hasTabNavigationEnabled(false)
                .hasProgressMessageShown(PROGRESS_MESSAGE)
                .hasTabsScrollingContainerHidden()
    }

    @Test
    fun `Should show tabs and re-enable tab navigation when hiding progress if previously enabled`() {
        //given
        stepperLayout.isTabNavigationEnabled = true
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //when
        feedbackType.hideProgress()

        //then
        assertStepperLayout()
                .hasTabNavigationEnabled(true)
                .hasProgressMessageHidden()
                .hasTabsScrollingContainerShown()
    }

    @Test
    fun `Should show tabs and leave tab navigation disabled when hiding progress if previously disabled`() {
        //given
        stepperLayout.isTabNavigationEnabled = false
        feedbackType.showProgress(PROGRESS_MESSAGE)

        //when
        feedbackType.hideProgress()

        //then
        assertStepperLayout()
                .hasTabNavigationEnabled(false)
                .hasProgressMessageHidden()
                .hasTabsScrollingContainerShown()
    }

    fun assertStepperLayout(): StepperLayoutAssert {
        return StepperLayoutAssert.assertThat(stepperLayout)
    }

}