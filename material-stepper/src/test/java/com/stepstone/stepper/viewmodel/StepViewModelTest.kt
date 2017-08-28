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
        val SUBTITLE = "my less fancy subtitle"

        val END_BUTTON_LABEL = "next or complete label"
        val BACK_BUTTON_LABEL = "back label"

        val END_BUTTON_LABEL_RES_ID = android.R.string.paste
        val BACK_BUTTON_LABEL_RES_ID = android.R.string.cancel

        val TITLE_RES_ID = android.R.string.copy
        val SUBTITLE_RES_ID = android.R.string.copyUrl

        val NEXT_BUTTON_ICON_RES_ID = android.R.drawable.ic_delete
        val BACK_BUTTON_ICON_RES_ID = android.R.drawable.ic_btn_speak_now

    }

    val mockContext = mock<Context> {
        on { getString(TITLE_RES_ID) } doReturn TITLE
        on { getString(SUBTITLE_RES_ID) } doReturn SUBTITLE
        on { getString(END_BUTTON_LABEL_RES_ID) } doReturn END_BUTTON_LABEL
        on { getString(BACK_BUTTON_LABEL_RES_ID) } doReturn BACK_BUTTON_LABEL
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
                .hasSubtitle(null)
                .hasBackButtonLabel(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
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
                .hasSubtitle(null)
                .hasBackButtonLabel(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
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
                .hasSubtitle(null)
                .hasBackButtonLabel(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
    }

    @Test
    fun `Should build StepViewModel with custom subtitle string`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setSubtitle(SUBTITLE)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasSubtitle(SUBTITLE)
                .hasTitle(null)
                .hasBackButtonLabel(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
    }

    @Test
    fun `Should build StepViewModel with custom subtitle resource`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setSubtitle(SUBTITLE_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasSubtitle(SUBTITLE)
                .hasTitle(null)
                .hasBackButtonLabel(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
    }

    @Test
    fun `Should build StepViewModel with custom Complete or Next button label string`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setEndButtonLabel(END_BUTTON_LABEL)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasEndButtonLabel(END_BUTTON_LABEL)
                .hasTitle(null)
                .hasSubtitle(null)
                .hasBackButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
    }

    @Test
    fun `Should build StepViewModel with custom Complete or Next button label resource`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setEndButtonLabel(END_BUTTON_LABEL_RES_ID)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasEndButtonLabel(END_BUTTON_LABEL)
                .hasTitle(null)
                .hasSubtitle(null)
                .hasBackButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
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
                .hasSubtitle(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
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
                .hasSubtitle(null)
                .hasEndButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
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
                .hasSubtitle(null)
                .hasEndButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
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
                .hasSubtitle(null)
                .hasEndButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonVisible(true)
                .hasEndButtonVisible(true)
    }

    @Test
    fun `Should build StepViewModel with hidden Back button`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setBackButtonVisible(false)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasBackButtonVisible(false)
                .hasTitle(null)
                .hasSubtitle(null)
                .hasEndButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasEndButtonVisible(true)
    }

    @Test
    fun `Should build StepViewModel with hidden Complete and Next button`() {
        //given
        val builder = StepViewModel.Builder(mockContext)
                .setEndButtonVisible(false)

        //when
        val stepViewModel = builder.create()

        //then
        assertThat(stepViewModel)
                .hasEndButtonVisible(false)
                .hasTitle(null)
                .hasSubtitle(null)
                .hasEndButtonLabel(null)
                .hasBackButtonLabel(null)
                .hasNextButtonEndDrawableResId(R.drawable.ms_ic_chevron_end)
                .hasBackButtonStartDrawableResId(R.drawable.ms_ic_chevron_start)
                .hasBackButtonVisible(true)
    }

}