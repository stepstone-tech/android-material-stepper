package com.stepstone.stepper.internal.widget.pagetransformer;

import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewPager;
import android.view.View;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * A {@link android.support.v4.view.ViewPager.PageTransformer} which reverses
 * the direction in which the pages slide in.
 *
 * @author Piotr Zawadzki
 */
@RestrictTo(LIBRARY)
public class StepperRtlPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        /* This moves the View to the other side.
        *
        * Lets assume that ViewPager has a width of 300dp.
        * This means that Views also have a width of 300dp.
        * The view that's in front and center has a position on screen equal to 0dp.
        * By view position we mean the position of view's left edge.
        *
        * Now, assuming we have a View on the right which is visible in 33% (its left 33% are visible)
        * we know that its actual position on the screen would be 200dp = 300dp * (100% - 33%).
        *
        * In this state from transformPage(..) method would be called with position = 0.67
        *
        * The line below would move the view by 400dp = -2 * 0.67 * 300dp to the left.
        * So effectively the view would be at -200dp and it's right 100dp would be visible.
        *
        * Thus, the view would be actually sliding to the other side which we expect when in RTL layout direction.
        */
        view.setTranslationX(-2 * position * view.getWidth());

    }
}
