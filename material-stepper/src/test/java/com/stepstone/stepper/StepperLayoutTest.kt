package com.stepstone.stepper

import android.widget.LinearLayout
import com.stepstone.stepper.test.*
import com.stepstone.stepper.test.assertion.StepperLayoutAssert
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner
import com.stepstone.stepper.test.test_double.SpyStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment


/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class StepperLayoutTest {

    companion object {

        const val ORIENTATION_HORIZONTAL = "horizontal"

        const val LAST_PAGE_INDEX = 2

    }

    lateinit var stepperLayout: StepperLayout

    @Test
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'progress_bar' type`() {
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
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'dots' type`() {
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
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'tabs' type`() {
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
    fun `All type specific indicators should be hidden when adapter is not set for StepperLayout with 'none' type`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_NONE)

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
                .hasTabsHidden()
    }

    @Test
    fun `All type specific indicators should be hidden when adapter is set for StepperLayout with 'none' type`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_NONE)

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasHorizontalProgressBarHidden()
                .hasDottedProgressBarHidden()
                .hasTabsHidden()
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

    @Test
    fun `Horizontal orientation should be set by default`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_DOTS)

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout().hasOrientation(LinearLayout.VERTICAL)
    }

    @Test
    fun `Bottom navigation should be visible by default`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout().hasBottomNavigationShown()
    }

    @Test
    fun `Bottom navigation should be hidden if 'ms_showBottomNavigation' attribute is set to 'false' in XML`() {
        //given
        val attributeSet = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, TYPE_TABS)
                .addAttribute(R.attr.ms_showBottomNavigation, "false")
                .build()

        //when
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //then
        assertStepperLayout().hasBottomNavigationHidden()
    }

    @Test
    fun `Bottom navigation should be hidden if set programmatically`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //when
        stepperLayout.setShowBottomNavigation(false)

        //then
        assertStepperLayout().hasBottomNavigationHidden()
    }

    @Test
    fun `Bottom navigation should be shown if set programmatically`() {
        //given
        val attributeSet = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.ms_stepperType, TYPE_TABS)
                .addAttribute(R.attr.ms_showBottomNavigation, "false")
                .build()
        stepperLayout = createStepperLayoutInActivity(attributeSet)

        //when
        stepperLayout.setShowBottomNavigation(true)

        //then
        assertStepperLayout().hasBottomNavigationShown()
    }

    @Test
    fun `Should show only 'Next' button on first page by default`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasBackButtonHidden()
                .hasNextButtonShown()
                .hasCompleteButtonHidden()
    }

    @Test
    fun `Should show 'Back' and 'Next' buttons on first page if specified in view attributes`() {
        //given
        val attributeSet = Robolectric.buildAttributeSet()
                        .addAttribute(R.attr.ms_stepperType, TYPE_TABS)
                        .addAttribute(R.attr.ms_showBackButtonOnFirstStep, true.toString())
                        .build()

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //then
        assertStepperLayout()
                .hasBackButtonShown()
                .hasNextButtonShown()
                .hasCompleteButtonHidden()
    }

    @Test
    fun `Should show 'Complete' button (and 'Back' button) on last page by default`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //when
        goToLastPage()

        //then
        assertStepperLayout()
                .hasBackButtonShown()
                .hasNextButtonHidden()
                .hasCompleteButtonShown()
    }

    @Test
    fun `Should show 'Back' and 'Next' buttons on middle page by default`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet)

        //when
        goToMiddlePage()

        //then
        assertStepperLayout()
                .hasBackButtonShown()
                .hasNextButtonShown()
                .hasCompleteButtonHidden()
    }

    @Test
    fun `Should hide 'Next' button on first page if specified in first step's StepViewModel`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)
        val firstStepViewModel = StepViewModel.Builder(RuntimeEnvironment.application)
                .setEndButtonVisible(false)
                .create()

        //when
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet, firstStepViewModel, null, null)

        //then
        assertStepperLayout()
                .hasBackButtonHidden()
                .hasNextButtonHidden()
                .hasCompleteButtonHidden()
    }

    @Test
    fun `Should hide 'Back' and 'Next' buttons on middle page if specified in middle step's StepViewModel`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)
        val middleStepViewModel = StepViewModel.Builder(RuntimeEnvironment.application)
                .setEndButtonVisible(false)
                .setBackButtonVisible(false)
                .create()
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet, null, middleStepViewModel, null)

        //when
        goToMiddlePage()

        //then
        assertStepperLayout()
                .hasBackButtonHidden()
                .hasNextButtonHidden()
                .hasCompleteButtonHidden()
    }

    @Test
    fun `Should hide 'Complete' button on last page if specified in last step's StepViewModel`() {
        //given
        val attributeSet = createAttributeSetWithStepperType(TYPE_TABS)
        val lastStepViewModel = StepViewModel.Builder(RuntimeEnvironment.application)
                .setEndButtonVisible(false)
                .create()
        stepperLayout = createStepperLayoutWithAdapterSetInActivity(attributeSet, null, null, lastStepViewModel)

        //when
        goToLastPage()

        //then
        assertStepperLayout()
                .hasBackButtonShown()
                .hasNextButtonHidden()
                .hasCompleteButtonHidden()
    }

    private fun goToLastPage() {
        stepperLayout.currentStepPosition = LAST_PAGE_INDEX
    }

    private fun goToMiddlePage() {
        stepperLayout.currentStepPosition = LAST_PAGE_INDEX - 1
    }

    fun assertStepperLayout(): StepperLayoutAssert {
        return StepperLayoutAssert.assertThat(stepperLayout)
    }

    fun assertFirstFragmentWasNotifiedAboutBeingSelected() {
        val stepAdapter = stepperLayout.adapter as SpyStepAdapter
        val firstStep = stepAdapter.steps.get(0)
        Assert.assertNotNull("Step not found", firstStep)
        Mockito.verify(firstStep).onSelected()
    }

}