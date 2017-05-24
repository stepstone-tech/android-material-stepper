package com.stepstone.stepper

import android.util.AttributeSet
import android.widget.LinearLayout
import com.stepstone.stepper.test.assertion.StepperLayoutAssert
import com.stepstone.stepper.test.assertion.StepperLayoutAssert.Companion.assertThat
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner
import com.stepstone.stepper.test.test_double.SpyStepAdapter
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController


/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class StepperLayoutTest {

    companion object {

        const val TYPE_PROGRESS_BAR = "progress_bar"
        const val TYPE_DOTS = "dots"
        const val TYPE_TABS = "tabs"

        const val ORIENTATION_HORIZONTAL = "horizontal"

    }

    lateinit var stepperLayout: StepperLayout

    @Test
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'progress_bar' type `() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_PROGRESS_BAR)

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
                .hasTabsHidden()
    }

    @Test
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'dots' type `() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_DOTS)

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasDottedProgressBarHidden()
                .hasHorizontalProgressBarHidden()
                .hasTabsHidden()
    }

    @Test
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'tabs' type `() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasTabsHidden()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
    }

    @Test
    fun `Horizontal progress bar should be shown when adapter is set for Stepper with 'progress_bar' type`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_PROGRESS_BAR)

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasHorizontalProgressBarShown()
                .hasDottedProgressBarHidden()
                .hasTabsHidden()
        assertFirstFragmentWasNotifiedAboutBeingSelected()
    }

    @Test
    fun `Dotted progress bar should be shown when adapter is set for Stepper with 'dots' type`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_DOTS)

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasDottedProgressBarShown()
                .hasHorizontalProgressBarHidden()
                .hasTabsHidden()
        assertFirstFragmentWasNotifiedAboutBeingSelected()
    }

    @Test
    fun `Tabs should be shown when adapter is set for Stepper with 'tabs' type`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasTabsShown()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
        assertFirstFragmentWasNotifiedAboutBeingSelected()
    }

    @Test
    fun `Horizontal orientation should be ignored if set in View attributes`() {
        //given
        val attributeSet = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, TYPE_DOTS)
                .addAttribute(android.R.attr.orientation, ORIENTATION_HORIZONTAL)
                .build()

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout().hasOrientation(LinearLayout.VERTICAL)
    }

    @Test
    fun `Horizontal orientation should be ignored if set programmatically`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_DOTS)
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //when
        stepperLayout.orientation = LinearLayout.HORIZONTAL

        //then
        assertStepperLayout().hasOrientation(LinearLayout.VERTICAL)
    }

    fun createAttributeSetWithStepperType(stepperType: String): AttributeSet {
        return Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, stepperType)
                .build()
    }

    fun createStepperLayoutInActivity(attributeSet: AttributeSet): StepperLayout {
        val activity = ActivityController.of(Robolectric.getShadowsAdapter(), StepperLayoutActivity().withStepperLayoutAttributes(attributeSet))
                .setup()
                .get()
        return activity.stepperLayout
    }

    fun createStepperLayoutWithAdapterSetInActivity(attributeSet: AttributeSet): StepperLayout {
        val activity = ActivityController.of(Robolectric.getShadowsAdapter(), StepperLayoutWithAdapterActivity().withStepperLayoutAttributes(attributeSet))
                .setup()
                .get()
        return activity.stepperLayout
    }

    fun assertStepperLayout(): StepperLayoutAssert {
        return assertThat(stepperLayout)
    }

    fun assertFirstFragmentWasNotifiedAboutBeingSelected() {
        val stepAdapter = stepperLayout.adapter as SpyStepAdapter
        val firstStep = stepAdapter.steps.get(0)
        assertNotNull("Step not found", firstStep)
        verify(firstStep).onSelected()
    }

}