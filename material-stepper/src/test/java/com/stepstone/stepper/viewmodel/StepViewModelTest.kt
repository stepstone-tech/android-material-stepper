package com.stepstone.stepper.viewmodel

import android.content.Context
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.stepstone.stepper.R
import com.stepstone.stepper.test.assertion.StepViewModelAssert.Companion.assertThat
import org.junit.Test

/**
 * @author Piotr Zawadzki
 */
class StepViewModelTest {

    companion object {

        val TITLE = "my fancy title"

        val NEXT_BUTTON_LABEL = "next label"
        val BACK_BUTTON_LABEL = "back label"
        val COMPLETE_BUTTON_LABEL = "complete label"

        val NEXT_BUTTON_LABEL_RES_ID = android.R.string.paste
        val BACK_BUTTON_LABEL_RES_ID = android.R.string.cancel
        val COMPLETE_BUTTON_LABEL_RES_ID = android.R.string.cut

        val TITLE_RES_ID = android.R.string.copy

        val NEXT_BUTTON_ICON_RES_ID = android.R.drawable.ic_delete
        val BACK_BUTTON_ICON_RES_ID = android.R.drawable.ic_btn_speak_now

    }

    val mockContext = mock<Context> {
        on { getString(TITLE_RES_ID) } doReturn TITLE
        on { getString(NEXT_BUTTON_LABEL_RES_ID) } doReturn NEXT_BUTTON_LABEL
        on { getString(BACK_BUTTON_LABEL_RES_ID) } doReturn BACK_BUTTON_LABEL
        on { getString(COMPLETE_BUTTON_LABEL_RES_ID) } doReturn COMPLETE_BUTTON_LABEL
    }

    @Test
    fun `Should build StepViewModel with default values`() {
        //given
        val builder = StepViewModel.Builder(mockContext)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasTitle(null)
                .hasBackButtonLabel(null)
                .hasNextButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom title string`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setTitle(TITLE)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasTitle(TITLE)
                .hasBackButtonLabel(null)
                .hasNextButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom title resource`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setTitle(TITLE_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasTitle(TITLE)
                .hasBackButtonLabel(null)
                .hasNextButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom next button label string`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setNextButtonLabel(NEXT_BUTTON_LABEL)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasNextButtonLabel(NEXT_BUTTON_LABEL)
                .hasTitle(null)
                .hasBackButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom next button label resource`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setNextButtonLabel(NEXT_BUTTON_LABEL_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasNextButtonLabel(NEXT_BUTTON_LABEL)
                .hasTitle(null)
                .hasBackButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom back button label string`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setBackButtonLabel(BACK_BUTTON_LABEL)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasBackButtonLabel(BACK_BUTTON_LABEL)
                .hasTitle(null)
                .hasNextButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom back button label resource`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setBackButtonLabel(BACK_BUTTON_LABEL_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasBackButtonLabel(BACK_BUTTON_LABEL)
                .hasTitle(null)
                .hasNextButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom complete button label string`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setCompleteButtonLabel(COMPLETE_BUTTON_LABEL)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasCompleteButtonLabel(COMPLETE_BUTTON_LABEL)
                .hasTitle(null)
                .hasNextButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom complete button label resource`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setCompleteButtonLabel(COMPLETE_BUTTON_LABEL_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasCompleteButtonLabel(COMPLETE_BUTTON_LABEL)
                .hasTitle(null)
                .hasNextButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

    @Test
    fun `Should build StepViewModel with custom next button end drawable`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setNextButtonEndDrawableResId(NEXT_BUTTON_ICON_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasNextButtonEndDrawableResId(NEXT_BUTTON_ICON_RES_ID)
                .hasTitle(null)
                .hasNextButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
    }

    @Test
    fun `Should build StepViewModel with custom back button start drawable`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setBackButtonStartDrawableResId(BACK_BUTTON_ICON_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasBackButtonStartDrawableResId(BACK_BUTTON_ICON_RES_ID)
                .hasTitle(null)
                .hasNextButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasCompleteButtonLabel(null)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
    }

}