package com.stepstone.stepper.internal.widget

import android.view.MotionEvent
import com.nhaarman.mockito_kotlin.mock
import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner::class)
class StepViewPagerTest {

    val mockTouchEvent: MotionEvent = mock { }

    val stepPager: StepViewPager = StepViewPager(RuntimeEnvironment.application)

    @Test
    fun `Should steal motion events from children if blocking children is enabled`() {
        //given
        stepPager.setBlockTouchEventsFromChildrenEnabled(true)

        //when
        val shouldStealMotionEventFromChildren = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertTrue(shouldStealMotionEventFromChildren)
    }

    @Test
    fun `Should not steal motion events from children by default`() {
        //when
        val shouldStealMotionEventFromChildren = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertFalse(shouldStealMotionEventFromChildren)
    }

    @Test
    fun `Should not handle events in 'onTouchEvent(_)' by default`() {
        //when
        val eventHandled = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertFalse(eventHandled)
    }

    @Test
    fun `Should handle touch events in 'onTouchEvent(_)' if blocking children is enabled`() {
        //when
        val eventHandled = stepPager.onInterceptTouchEvent(mockTouchEvent)

        //then
        assertFalse(eventHandled)
    }

}