package com.stepstone.stepper.internal.widget

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.view.ContextThemeWrapper
import com.nhaarman.mockito_kotlin.whenever
import com.stepstone.stepper.R
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class StepTabTest {

    companion object {
        val TITLE: CharSequence = "dummy title"
        val SUBTITLE: CharSequence = "dummy subtitle"
        val NEW_SUBTITLE: CharSequence = "new fancy subtitle"
        val ERROR_MESSAGE = "Oooops"
    }

    @Mock
    lateinit var mockVerificationError: VerificationError

    lateinit var tab: StepTab

    @SuppressLint("RestrictedApi")
    @Before
    fun setUp() {
        val contextThemeWrapper = ContextThemeWrapper(RuntimeEnvironment.application, RuntimeEnvironment.application.theme)
        contextThemeWrapper.setTheme(R.style.MSDefaultStepperLayoutTheme)
        tab = StepTab(contextThemeWrapper)
    }

    @Test
    fun `Should create a default tab in inactive state`() {
        assertTab()
                .isNotNull
                .isInState(StepTab.InactiveNumberState::class.java)
                .isSubtitleHidden()
    }

    @Test
    fun `Should set new title`() {
        //when
        tab.setStepTitle(TITLE)

        //then
        assertTab()
                .hasTitle(TITLE)
    }

    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.LOLLIPOP))
    @Test
    fun `Should set a new subtitle`() {
        //when
        tab.setStepSubtitle(SUBTITLE)

        //then
        assertTab()
                .hasSubtitle(SUBTITLE)
    }

    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.LOLLIPOP))
    @Test
    fun `Should keep the same subtitle if it did not change`() {
        //given
        tab.setStepSubtitle(SUBTITLE)

        //when
        tab.setStepSubtitle(SUBTITLE)

        //then
        assertTab()
                .hasSubtitle(SUBTITLE)
    }

    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.LOLLIPOP))
    @Test
    fun `Should hide the subtitle if new one is empty`() {
        //given
        tab.setStepSubtitle(SUBTITLE)

        //when
        tab.setStepSubtitle("")

        //then
        assertTab()
                .isSubtitleHidden()
    }

    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.LOLLIPOP))
    @Test
    fun `Should hide the subtitle if new one is 'null'`() {
        //given
        tab.setStepSubtitle(SUBTITLE)

        //when
        tab.setStepSubtitle(null)

        //then
        assertTab()
                .isSubtitleHidden()
    }

    @Config(sdk = intArrayOf(Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.LOLLIPOP))
    @Test
    fun `Should update the subtitle if it changed`() {
        //given
        tab.setStepSubtitle(SUBTITLE)

        //when
        tab.setStepSubtitle(NEW_SUBTITLE)

        //then
        assertTab()
                .hasSubtitle(NEW_SUBTITLE)
    }

    @Test
    fun `Updating state with an error should change state to Warning state`() {
        //when
        tab.updateState(mockVerificationError, false, false, false)

        //then

        assertTab()
                .isInState(StepTab.WarningState::class.java)
    }

    @Test
    fun `Should not update the subtitle if already set when changing to Warning state if 'showErrorMessageEnabled' is false`() {
        //given
        tab.setStepSubtitle(SUBTITLE)

        //when
        tab.updateState(mockVerificationError, false, false, false)

        //then

        assertTab()
                .isInState(StepTab.WarningState::class.java)
                .hasSubtitle(SUBTITLE)
    }

    @Test
    fun `Should not update the subtitle if missing when changing to Warning state if 'showErrorMessageEnabled' is false`() {
        //when
        tab.updateState(mockVerificationError, false, false, false)

        //then

        assertTab()
                .isInState(StepTab.WarningState::class.java)
                .isSubtitleHidden()
    }

    @Test
    fun `Should show error message instead of the subtitle when changing to Warning state if 'showErrorMessageEnabled' is true and subtitle was provided before`() {
        //given
        tab.setStepSubtitle(SUBTITLE)
        whenever(mockVerificationError.errorMessage).thenReturn(ERROR_MESSAGE)

        //when
        tab.updateState(mockVerificationError, false, false, true)

        //then

        assertTab()
                .isInState(StepTab.WarningState::class.java)
                .hasSubtitle(ERROR_MESSAGE)
    }

    @Test
    fun `Should show error message in place of the subtitle when changing to Warning state if 'showErrorMessageEnabled' is true and subtitle was missing before`() {
        //given
        whenever(mockVerificationError.errorMessage).thenReturn(ERROR_MESSAGE)

        //when
        tab.updateState(mockVerificationError, false, false, true)

        //then

        assertTab()
                .isInState(StepTab.WarningState::class.java)
                .hasSubtitle(ERROR_MESSAGE)
    }

    @Test
    fun `Updating state without an error should change state to Done state when step is done`() {
        //when
        tab.updateState(null, true, false, false)

        //then

        assertTab()
                .isInState(StepTab.DoneState::class.java)
    }

    @Test
    fun `Updating state without an error should change state to Active number state when step is not done but it is the current step`() {
        //when
        tab.updateState(null, false, true, false)

        //then

        assertTab()
                .isInState(StepTab.ActiveNumberState::class.java)
    }

    @Test
    fun `Updating state without an error should change state to Inactive number state when step is not done and it is not the current step`() {
        //when
        tab.updateState(null, false, false, false)

        //then

        assertTab()
                .isInState(StepTab.InactiveNumberState::class.java)
    }

    private fun assertTab() = StepTabAssert.assertThat(tab)

}