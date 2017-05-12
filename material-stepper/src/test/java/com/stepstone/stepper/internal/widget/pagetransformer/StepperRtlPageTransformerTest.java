package com.stepstone.stepper.internal.widget.pagetransformer;

import android.view.View;

import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner.class)
public class StepperRtlPageTransformerTest {

    private static final int VIEW_WIDTH = 320;

    @Mock
    View mockView;

    StepperRtlPageTransformer pageTransformer = new StepperRtlPageTransformer();

    @Before
    public void setUp() {
        when(mockView.getWidth()).thenReturn(VIEW_WIDTH);
    }

    @Test
    public void should_reset_translationX_when_at_front_center() {
        //when
        pageTransformer.transformPage(mockView, 0.0f);

        //then
        verify(mockView).setTranslationX(isZero());
    }

    @Test
    public void should_reverse_view_when_at_one_full_position_to_the_right() {
        //when
        pageTransformer.transformPage(mockView, 1.0f);

        //then
        verify(mockView).setTranslationX(-2 * VIEW_WIDTH);
    }

    @Test
    public void should_reverse_view_when_at_one_full_position_to_the_left() {
        //when
        pageTransformer.transformPage(mockView, -1.0f);

        //then
        verify(mockView).setTranslationX(2 * VIEW_WIDTH);
    }

    @Test
    public void should_reverse_view_when_in_the_middle() {
        //when
        pageTransformer.transformPage(mockView, 0.5f);

        //then
        verify(mockView).setTranslationX(-VIEW_WIDTH);
    }

    private float isZero() {
        return or(eq(0.0f), eq(-0.0f));
    }

}