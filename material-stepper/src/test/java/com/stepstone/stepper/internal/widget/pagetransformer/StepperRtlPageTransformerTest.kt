package com.stepstone.stepper.internal.widget.pagetransformer

import android.view.View
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.mockito.AdditionalMatchers.or
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.verify

/**
 * @author Piotr Zawadzki
 */
class StepperRtlPageTransformerTest {

    companion object {

        const val VIEW_WIDTH = 320

    }

    val mockView: View = mock {
        on { width } doReturn VIEW_WIDTH
    }

    val pageTransformer = StepperRtlPageTransformer()

    @Test
    fun `Should reset 'translationX' when page is at front center`() {
        //when
        pageTransformer.transformPage(mockView, 0.0f)

        //then
        verify(mockView).translationX = isZero
    }

    @Test
    fun `Should reverse view position on X axis when page is at one full position to the right`() {
        //when
        pageTransformer.transformPage(mockView, 1.0f)

        //then
        verify(mockView).translationX = -2.0f * VIEW_WIDTH
    }

    @Test
    fun `Should reverse view position on X axis when page is at one full position to the left`() {
        //when
        pageTransformer.transformPage(mockView, -1.0f)

        //then
        verify(mockView).translationX = 2.0f * VIEW_WIDTH
    }

    @Test
    fun `Should reverse view position on X axis when page is half moved`() {
        //when
        pageTransformer.transformPage(mockView, 0.5f)

        //then
        verify(mockView).translationX = -VIEW_WIDTH.toFloat()
    }

    val isZero: Float
        get() = or(eq(0.0f), eq(-0.0f))

}